/*
 * Copyright (C) 2020, TIBCO Software Inc.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.tibco.ep.sb.services.stubs.admin;

import com.tibco.ep.sb.services.management.IContext;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * The context stub implementation
 */
public class Context extends ServiceAwareStub implements IContext {

    private final Map<String, String> env = new HashMap<>();

    /**
     * @param adminService The admin service
     * @param installation The installation path
     */
    public Context(AdminService adminService, Path installation) {
        super(adminService);
        logMethod("constructor", installation);
    }

    @Override
    public IContext withTracingEnabled() {
        logMethod("enableTracing");
        return this;
    }

    @Override
    public IContext withEnvironment(Map<String, String> environment) {
        logMethod("setEnvironment", environment);

        env.clear();
        env.putAll(environment);

        return this;
    }

    @Override
    public void clearEnvironment() {
        logMethod("clearEnvironment");
        env.clear();
    }

    @Override
    public String toString() {
        return "Context{" +
            "env=" + env +
            '}';
    }
}
