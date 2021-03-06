/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at

 *  http://www.apache.org/licenses/LICENSE-2.0

 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.stratos.cli.commands;

import org.apache.commons.cli.*;
import org.apache.stratos.cli.Command;
import org.apache.stratos.cli.RestCommandLineService;
import org.apache.stratos.cli.StratosCommandContext;
import org.apache.stratos.cli.exception.CommandException;
import org.apache.stratos.cli.utils.CliConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListPartialSearchTenantsCommand implements Command<StratosCommandContext>{

    private static final Logger logger = LoggerFactory.getLogger(ListCartridgesByFilterCommand.class);

    private final Options options;

    public ListPartialSearchTenantsCommand(){
        options = constructOptions();
    }

    private Options constructOptions() {
        final Options options = new Options();

        Option partialDomainOption = new Option(CliConstants.TENANT_PARTIAL_SEARCH_OPTION, CliConstants.TENANT_PARTIAL_SEARCH_LONG_OPTION, true,
                "partialDomain");
        partialDomainOption.setArgName("partialDomain");
        options.addOption(partialDomainOption);

        return options;
    }

    public String getName() {
        return "list-tenants-by-partial-domain";
    }

    public String getDescription() {
        return "List cartridges by partial domain search";
    }

    public String getArgumentSyntax() {
        return null;
    }

    public int execute(StratosCommandContext context, String[] args) throws CommandException {
        if (logger.isDebugEnabled()) {
            logger.debug("Executing {} command...", getName());
        }

        if (args != null && args.length > 0) {
            String partialDomain= null;

            final CommandLineParser parser = new GnuParser();
            CommandLine commandLine;

            try {
                commandLine = parser.parse(options, args);

                if (logger.isDebugEnabled()) {
                    logger.debug("List cartridges by partial domain search");
                }

                if (commandLine.hasOption(CliConstants.TENANT_PARTIAL_SEARCH_OPTION)) {
                    if (logger.isTraceEnabled()) {
                        logger.trace("Partial domain option is passed");
                    }
                    partialDomain = commandLine.getOptionValue(CliConstants.TENANT_PARTIAL_SEARCH_OPTION);
                }

                if (partialDomain == null) {
                    System.out.println("usage: " + getName() + "usage: " + getName() + " [-d <partialDomain>]");
                    return CliConstants.COMMAND_FAILED;
                }

                RestCommandLineService.getInstance().listTenantsByPartialDomain(partialDomain);
                return CliConstants.COMMAND_SUCCESSFULL;

            } catch (ParseException e) {
                if (logger.isErrorEnabled()) {
                    logger.error("Error parsing arguments", e);
                }
                System.out.println(e.getMessage());
                return CliConstants.COMMAND_FAILED;
            }

        } else {
            context.getStratosApplication().printUsage(getName());
            return CliConstants.COMMAND_FAILED;
        }
    }

    public Options getOptions() {
        return options;
    }
}