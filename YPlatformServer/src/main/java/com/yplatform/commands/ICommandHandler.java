package com.yplatform.commands;

import com.google.gson.Gson;
import com.yplatform.network.ExitException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface ICommandHandler<T extends ICommand<TResult>, TResult> {
    TResult Handle() throws IOException, ExitException;
}

