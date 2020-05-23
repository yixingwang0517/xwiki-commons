/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.crypto.internal.symmetric.generator;

import org.junit.jupiter.api.Test;
import org.xwiki.crypto.internal.DefaultSecureRandomProvider;
import org.xwiki.crypto.params.generator.symmetric.GenericKeyGenerationParameters;
import org.xwiki.crypto.params.generator.KeyGenerationParameters;
import org.xwiki.test.annotation.ComponentList;
import org.xwiki.test.junit5.mockito.ComponentTest;
import org.xwiki.test.junit5.mockito.InjectMockComponents;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ComponentTest
// @formatter:off
@ComponentList({
    DefaultSecureRandomProvider.class
})
// @formatter:on
class DefaultKeyGeneratorTest
{
    @InjectMockComponents
    private DefaultKeyGenerator generator;

    @Test
    void generateWithoutArgument()
    {
        Throwable exception = assertThrows(UnsupportedOperationException.class,
            () -> generator.generate());
        assertEquals("Knowing the key strength is required to generate a key.", exception.getMessage());
    }

    @Test
    void generateWithStrengthParameter()
    {
        KeyGenerationParameters params = new GenericKeyGenerationParameters(128);
        byte[] key = generator.generate(params);

        assertThat(key.length, equalTo(128));
        assertThat(key, not(equalTo(generator.generate(params))));
        assertThat(generator.generate(new GenericKeyGenerationParameters(16)).length, equalTo(16));
    }
}
