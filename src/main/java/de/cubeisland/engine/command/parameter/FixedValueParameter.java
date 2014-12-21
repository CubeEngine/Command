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
package de.cubeisland.engine.command.parameter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.cubeisland.engine.command.CommandInvocation;

public class FixedValueParameter extends SimpleParameter
{
    private List<String> fixedValues = new ArrayList<>();

    public FixedValueParameter(Class<? extends FixedValues> type, Class<?> reader)
    {
        super(type, reader, 1);
        try
        {
            Method valuesMethod = type.getMethod("values");
            for (FixedValues value : (FixedValues[])valuesMethod.invoke(null))
            {
                fixedValues.add(value.getName());
            }
        }
        catch (ReflectiveOperationException e)
        {
            throw new IllegalArgumentException("Error while extracting Fixed Values from class", e);
        }
    }

    @Override
    protected boolean isPossible(CommandInvocation invocation)
    {
        String token = invocation.currentToken();
        for (String fixedValue : fixedValues)
        {
            if (fixedValue.equalsIgnoreCase(token))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    protected List<String> getSuggestions(CommandInvocation invocation)
    {
        List<String> result = new ArrayList<>();
        String token = invocation.currentToken().toLowerCase();
        for (String fixedValue : fixedValues)
        {
            if (fixedValue.startsWith(token))
            {
                result.add(fixedValue);
            }
        }
        return result;
    }

    @Override
    public void parse(CommandInvocation invocation)
    {
        List<ParsedParameter> result = invocation.valueFor(ParsedParameters.class);
        String token = invocation.currentToken();
        for (String fixedValue : fixedValues)
        {
            if (fixedValue.equalsIgnoreCase(token))
            {
                result.add(ParsedParameter.of(this, fixedValue, token));
                return;
            }
        }
        throw new IllegalStateException();
    }

    public List<String> getFixedValues()
    {
        return fixedValues;
    }
}
