# Java BencheMarking Test Tools
## Env.
```yaml

plugin {
  id 'me.champeau.jmh' version '0.6.6' //JMH Plugin
  id 'com.github.johnrengelman.shadow' version '7.1.2'
}
  
dependencys {
  jmh 'commons-io:commons-io:2.4'
  jmh 'org.openjdk.jmh:jmh-core:0.9'
  jmh 'org.openjdk.jmh:jmh-generator-annprocess:0.9'
}

  //JMH BencheMarking Tools Use (디렉토리가 다르게 설정되어 있어야됨)
jmhJar {
    zip64 = true  //shadow jar with > 65535 files fails
}

jmh {
    iterations = 1 // Number of measurement iterations to do.
//    benchmarkMode = ['thrpt','ss'] // Benchmark mode. Available modes are: [Throughput/thrpt, AverageTime/avgt, SampleTime/sample, SingleShotTime/ss, All/all]
//    batchSize = 1 // Batch size: number of benchmark method calls per operation. (some benchmark modes can ignore this setting)
    fork = 1 // How many times to forks a single benchmark. Use 0 to disable forking altogether
    humanOutputFile = project.file("${project.buildDir}/reports/jmh/human.txt") // human-readable output file
    resultsFile = project.file("${project.buildDir}/reports/jmh/results.txt") // results file
//    operationsPerInvocation = 10 // Operations per invocation.
//    benchmarkParameters =  [:] // Benchmark parameters.
//    profilers = [] // Use profilers to collect additional data. Supported profilers: [cl, comp, gc, stack, perf, perfnorm, perfasm, xperf, xperfasm, hs_cl, hs_comp, hs_gc, hs_rt, hs_thr, async]
//    timeOnIteration = '1s' // Time to spend at each measurement iteration.
    resultFormat = 'CSV' // Result format type (one of CSV, JSON, NONE, SCSV, TEXT)
//    synchronizeIterations = false // Synchronize iterations?
//    threads = 4 // Number of worker threads to run with.
//    threadGroups = [2,3,4] //Override thread group distribution for asymmetric benchmarks.
    timeUnit = 'ms' // Output time unit. Available time units are: [m, s, ms, us, ns].
    verbosity = 'NORMAL' // Verbosity mode. Available modes are: [SILENT, NORMAL, EXTRA]
    warmup = '1s' // Time to spend at each warmup iteration.
    warmupBatchSize = 10 // Warmup batch size: number of benchmark method calls per operation.
    warmupForks = 0 // How many warmup forks to make for a single benchmark. 0 to disable warmup forks.
    warmupIterations = 1 // Number of warmup iterations to do.
//    warmupMode = 'INDI' // Warmup mode for warming up selected benchmarks. Warmup modes are: [INDI, BULK, BULK_INDI].
//    warmupBenchmarks = ['.*Warmup'] // Warmup benchmarks to include in the run in addition to already selected. JMH will not measure these benchmarks, but only use them for the warmup.

    zip64 = true // Use ZIP64 format for bigger archives (shadow jar with > 65535 files fails)
//    jmhVersion = '1.29' // Specifies JMH version
    includeTests = true // Allows to include test sources into generate JMH jar, i.e. use it when benchmarks depend on the test classes.
    duplicateClassesStrategy = DuplicatesStrategy.WARN // Strategy to apply when encountring duplicate classes during creation of the fat jar (i.e. while executing jmhJar task)
}

```

### Prefix Directory
```yaml
/src/jmh/java
/src/jmh/resource

-> Project Setting 해당 두 디렉토리를 classpath에 지정
```

위 두 설정이 완료되면  https://www.baeldung.com/java-microbenchmark-harness 각각의 annotation을 활용해 성능 테스트릴 진행할수 있다.
```shell
./gradlew clean jmh
```