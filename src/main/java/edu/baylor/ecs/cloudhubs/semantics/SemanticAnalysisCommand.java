package edu.baylor.ecs.cloudhubs.semantics;

import edu.baylor.ecs.cloudhubs.semantics.util.MsCache;
import edu.baylor.ecs.cloudhubs.semantics.util.ProcessFiles;
import edu.baylor.ecs.cloudhubs.semantics.util.factory.*;
import edu.baylor.ecs.cloudhubs.semantics.util.file.CacheManager;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class SemanticAnalysisCommand implements QuarkusApplication {

    public static String sutPath;
    public static String cachePath;

    @Override
    public int run(String... args) throws Exception {
        long start = System.currentTimeMillis();
        initCache();
        initPaths(args);
        preProcess();
        processCodeClonesFromCache();
        conductCalculation();
        persistCache();
        System.out.println(System.currentTimeMillis() - start);
        return 0;
    }

    private void persistCache() {
        CacheManager cacheManager = new CacheManager();
        cacheManager.persistCache(cachePath);
    }

    private void conductCalculation() {
        ModuleCloneFactory moduleCloneFactory = new ModuleCloneFactory();
        moduleCloneFactory.createData();
    }

    private void initPaths(String... args) {
        String[] split = args[0].split(",");
        sutPath = split[0];
        cachePath = split[1];
//        sutPath = "/Users/jan/Development/train-ticket/";
//        cachePath = "/Users/jan/Development/data/";
//        sutPath = "C:\\git\\train-ticket";
//        cachePath = "C:\\git\\data";
    }


    public void initCache(){
        MsCache.init();
    }

    public void preProcess() {
        ProcessFiles.run(sutPath);
        FlowBuilder flowBuilder = new FlowBuilder();
        flowBuilder.buildFlows();
    }

    public void processCodeClonesFromCache() {
//        CacheManager cacheManager = new CacheManager();
//        cacheManager.recreateCache(cachePath);
        CodeClonesFactory codeClonesFactory = new CodeClonesFactory();
        codeClonesFactory.findCodeClones();
        ModuleClonePairFactory mcpf = new ModuleClonePairFactory();
        mcpf.printModuleClonePairs();
    }

}
