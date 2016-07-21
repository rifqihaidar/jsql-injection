package com.jsql.model.accessible;

import java.util.concurrent.Callable;

import com.jsql.model.MediatorModel;
import com.jsql.model.exception.InjectionFailureException;
import com.jsql.model.exception.StoppedByUserException;
import com.jsql.model.suspendable.SuspendableGetRows;

/**
 * Callable to read file source code.
 */
public class CallableFile implements Callable<CallableFile> {
    /**
     * Url of the file to read.
     */
    private String pathFile;

    /**
     * Source code of file.
     */
    private String sourceFile;

    /**
     * Create Callable to read a file.
     * @param pathFile
     */
    public CallableFile(String pathFile) {
        this.pathFile = pathFile;
    }

    @Override
    public CallableFile call() throws Exception {
//        if (!RessourceAccess.isSearchFileStopped) {
            String[] sourcePage = {""};

            String resultToParse = "";
            try {
                resultToParse = new SuspendableGetRows().run(
                    MediatorModel.model().vendor.getValue().getSqlReadFile(pathFile),
                    sourcePage,
                    false,
                    1,
                    null
                );
            } catch (InjectionFailureException e) {
                // File does not exist probably
            }
            sourceFile = resultToParse;
//        }
        return this;
    }
    
    public String getUrl() {
        return pathFile;
    }

    public String getFileSource() {
        return sourceFile;
    }
}