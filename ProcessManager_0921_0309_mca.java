// 代码生成时间: 2025-09-21 03:09:57
package org.acme.processmanager;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ExitCode;

import java.util.concurrent.Callable;
import java.util.List;

@QuarkusMain
@Command(name = "process-manager", mixinStandardHelpOptions = true, showDefaultValues = true, 
         description = "A process manager CLI application using Quarkus")
public class ProcessManager implements Callable<Integer> {

    @Option(names = { "-k", "--kill" }, description = "Kill the specified process by PID")
    private int killProcessId;

    @Option(names = { "-l", "--list" }, description = "List all running processes")
    private boolean listProcesses = false;

    @Parameters(paramLabel = "<process-name>