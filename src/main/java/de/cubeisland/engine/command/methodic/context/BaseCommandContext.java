/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Anselm Brehme, Phillip Schichtel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.cubeisland.engine.command.methodic.context;

import java.util.List;

import de.cubeisland.engine.command.CommandSource;
import de.cubeisland.engine.command.tokenized.TokenizedInvocation;

/**
 * A CommandContext for an Invocation of a MethodicCommand
 */
public class BaseCommandContext
{
    private final TokenizedInvocation call;

    public BaseCommandContext(TokenizedInvocation call)
    {
        this.call = call;
    }

    /**
     * Returns the CommandCall
     *
     * @return the CommandCall
     */
    public TokenizedInvocation getCall()
    {
        return call;
    }

    /**
     * Returns the parent calls
     *
     * @return the parent calls
     */
    public List<String> getParentCalls()
    {
        return call.getParentCalls();
    }

    /**
     * Returns the source of the commands invocation
     *
     * @return the CommandSource
     */
    public CommandSource getSource()
    {
        return call.getCommandSource();
    }

    /**
     * Returns whether the source is of given type
     *
     * @param clazz the type
     * @return whether the source is of given type
     */
    public boolean isSource(Class<? extends CommandSource> clazz)
    {
        return clazz.isAssignableFrom(this.getSource().getClass());
    }
}
