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
package de.cubeisland.engine.command.old;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import static java.util.Locale.ENGLISH;

public abstract class BaseCommand
{
    final Set<String> aliases = new HashSet<>();
    private final Map<String, BaseCommand> children = new TreeMap<>();
    protected BaseCommand parent;
    String name;
    String description;
    Object contextFactory;

    private Map<Object, Object> commandDescriptor;

    protected BaseCommand()
    {
    }

    /**
     * Returns the parent of this command or null if there is none
     *
     * @return the parent command or null
     */
    public BaseCommand getParent()
    {
        return parent;
    }

    /**
     * Returns a brief description of this command
     *
     * @return a command-description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Returns the ContextFactory for this command
     *
     * @return the conextfactory
     */
    public Object getContextFactory()
    {
        return this.contextFactory;
    }

    /**
     * Adds a child to this command
     *
     * @param command the command to add
     */
    @SuppressWarnings("unchecked")
    public void addChild(BaseCommand command)
    {
        if (command == null)
        {
            throw new IllegalArgumentException("The command must not be null!");
        }
        if (this == command)
        {
            throw new IllegalArgumentException("You can't register a command as a child of itself!");
        }

        if (command.getParent() != null)
        {
            throw new IllegalArgumentException("The given command is already registered! Use aliases instead!");
        }

        this.children.put(command.getName(), command);
        command.parent = this;
        for (String alias : command.getAliases())
        {
            alias = alias.toLowerCase(ENGLISH);
            this.children.put(alias, command);
        }
    }

    /**
     * Returns a child command by name without typo correction
     *
     * @param name the child name
     *
     * @return the child or null if not found
     */
    @SuppressWarnings("unchecked")
    public BaseCommand getChild(String name)
    {
        return name == null ? null : this.children.get(name.toLowerCase(ENGLISH));
    }

    /**
     * Returns the name of this command
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the aliases for this command
     *
     * @return the aliases
     */
    public Set<String> getAliases()
    {
        return new HashSet<>(this.aliases);
    }


    /**
     * Checks whether this command has a child with the given name
     *
     * @param name the name to check for
     *
     * @return true if a matching command was found
     */
    public boolean hasChild(String name)
    {
        return name != null && this.children.containsKey(name.toLowerCase());
    }


    /**
     * Checks whether this command has children
     *
     * @return true if that is the case
     */
    public boolean hasChildren()
    {
        return !this.children.isEmpty();
    }

    /**
     * Returns a Set of all children
     *
     * @return a Set of children
     */
    public Set<BaseCommand> getChildren()
    {
        return new HashSet<>(this.children.values());
    }

    /**
     * Removes a child from this command
     *
     * @param name the name fo the child
     */
    public void removeChild(String name)
    {
        BaseCommand cmd = this.getChild(name);
        Iterator<Entry<String, BaseCommand>> it = this.children.entrySet().iterator();

        while (it.hasNext())
        {
            if (it.next().getValue() == cmd)
            {
                it.remove();
            }
        }
        cmd.parent = null;
    }

    /**
     * This method calls the CommandRunner or can be overwritten to handle running the command itself
     *
     * @param ctx the CommandContext
     *
     * @return the CommandResult
     *

    public CommandResult run(Object ctx)
    {
        return this.runner.run(ctx);
    }

    /**
     * Checks if given context is applicable to this command
     *
     * @param ctx the context to check
     */
    public void checkContext(Object ctx)
    {
        /*
        CtxDescriptor descriptor = ctx.getCommand().getContextFactory().descriptor();

        // TODO use only by certain classes throw new RestrictedUsageException(sourceClass);

        ArgBounds bounds = descriptor.getArgBounds();
        if (ctx.getIndexedCount() < bounds.getMin())
        {
            throw new TooFewArgumentsException();
        }

        if (bounds.getMax() > ArgBounds.NO_MAX && ctx.getIndexedCount() > bounds.getMax())
        {
            throw new TooManyArgumentsException();
        }

        for (NamedParameter named : descriptor.getNamedGroups().listAll())
        {
            if (named.isRequired() && named.isInRequiredGroup())
            {
                if (!(ctx.hasNamed(named.getName())))
                {
                    throw new MissingParameterException(named.getName());
                }
            }
        }
        */
    }
}
