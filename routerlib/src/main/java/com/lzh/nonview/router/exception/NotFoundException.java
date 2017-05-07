/*
 * Copyright (C) HaoGe <a href="https://github.com/yjfnypeu"/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lzh.nonview.router.exception;

/**
 * The custom exception represents not found
 * @author haoge
 */
public class NotFoundException extends RuntimeException {

    /**
     * This type represents not matching to the corresponding uri routing rules
     */
    public static final int TYPE_SCHEMA = 0;
    /**
     * This type represents the uri matching to the routing target does not exist
     */
    public static final int TYPE_CLZ = 1;

    private final int type;
    private final String notFoundName;

    /**
     * @param detailMessage detail error message
     * @param type one of {@link NotFoundException#TYPE_SCHEMA} and {@link NotFoundException#TYPE_SCHEMA}
     * @param notFoundName The routing target name matched with uri.
     * @see NotFoundException#TYPE_SCHEMA
     * @see NotFoundException#TYPE_CLZ
     */
    public NotFoundException(String detailMessage, int type,String notFoundName) {
        super(detailMessage);
        this.type = type;
        this.notFoundName = notFoundName;
    }

    /**
     * @return the type of not found. it could be one of the {@link NotFoundException#TYPE_SCHEMA} and {@link NotFoundException#TYPE_SCHEMA}
     * @see NotFoundException#TYPE_SCHEMA
     * @see NotFoundException#TYPE_CLZ
     */
    public int getType() {
        return type;
    }

    /**
     * @return the uri matching routing target class name.
     */
    public String getNotFoundName() {
        return notFoundName;
    }

}
