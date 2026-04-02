package controller;

import exceptions.MyException;
import model.values.RefValue;
import model.values.Value;
import repo.IRepository;
import state.PrgState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public List<PrgState> getPrgList() {
        return repo.getPrgList();
    }

    public void shutdownExecutor() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) {
        ensureExecutor();
        prgList.forEach(repo::logPrgStateExec);

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) p::oneStep)
                .collect(Collectors.toList());

        try {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new MyException(e.getMessage());
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            prgList.addAll(newPrgList);
        } catch (InterruptedException e) {
            throw new MyException(e.getMessage());
        }

        prgList.forEach(repo::logPrgStateExec);
        repo.setPrgList(prgList);
    }

    public void allStep() {
        ensureExecutor();
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (prgList.size() > 0) {
            if (!prgList.isEmpty()) {
                prgList.get(0).getHeap().setContent(
                        safeGarbageCollector(
                                getAddrFromSymTable(prgList),
                                prgList.get(0).getHeap().getContent()
                        )
                );
            }
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        shutdownExecutor();
        repo.setPrgList(prgList);
    }

    public void oneStepGui() {
        ensureExecutor();
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        if (prgList.isEmpty()) {
            repo.setPrgList(prgList);
            shutdownExecutor();
            return;
        }
        prgList.get(0).getHeap().setContent(
                safeGarbageCollector(
                        getAddrFromSymTable(prgList),
                        prgList.get(0).getHeap().getContent()
                )
        );
        oneStepForAllPrg(prgList);
        prgList = removeCompletedPrg(repo.getPrgList());
        repo.setPrgList(prgList);
        if (prgList.isEmpty()) {
            shutdownExecutor();
        }
    }

    List<Integer> getAddrFromSymTable(List<PrgState> prgList) {
        return prgList.stream()
                .map(p -> p.getSymTable().getContent().values())
                .flatMap(Collection::stream)
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddr())
                .collect(Collectors.toList());
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {

        List<Integer> referencedAddrs = new ArrayList<>(symTableAddr);

        boolean change = true;
        while (change) {
            change = false;
            List<Integer> newAddrs = heap.entrySet().stream()
                    .filter(e -> referencedAddrs.contains(e.getKey()))
                    .map(Map.Entry::getValue)
                    .filter(v -> v instanceof RefValue)
                    .map(v -> ((RefValue) v).getAddr())
                    .filter(addr -> !referencedAddrs.contains(addr))
                    .toList();

            if (!newAddrs.isEmpty()) {
                referencedAddrs.addAll(newAddrs);
                change = true;
            }
        }

        return heap.entrySet().stream()
                .filter(e -> referencedAddrs.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void ensureExecutor() {
        if (executor == null || executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(2);
        }
    }

}
