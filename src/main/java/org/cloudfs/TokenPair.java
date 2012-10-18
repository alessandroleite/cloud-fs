/**
 * ====
 *     ====
 *         ====
 *             Copyright (c) 2012 Alessandro F. Leite
 *
 *             Permission is hereby granted, free of charge, to any person obtaining
 *             a copy of this software and associated documentation files (the
 *             "Software"), to deal in the Software without restriction, including
 *             without limitation the rights to use, copy, modify, merge, publish,
 *             distribute, sublicense, and/or sell copies of the Software, and to
 *             permit persons to whom the Software is furnished to do so, subject to
 *             the following conditions:
 *
 *             The above copyright notice and this permission notice shall be
 *             included in all copies or substantial portions of the Software.
 *
 *             THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *             EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *             MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *             NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *             LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *             OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *             WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *         ====
 *
 *         Copyright (c) 2012 Alessandro F. Leite
 *
 *         Permission is hereby granted, free of charge, to any person obtaining
 *         a copy of this software and associated documentation files (the
 *         "Software"), to deal in the Software without restriction, including
 *         without limitation the rights to use, copy, modify, merge, publish,
 *         distribute, sublicense, and/or sell copies of the Software, and to
 *         permit persons to whom the Software is furnished to do so, subject to
 *         the following conditions:
 *
 *         The above copyright notice and this permission notice shall be
 *         included in all copies or substantial portions of the Software.
 *
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *         EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *         MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *         NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *         LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *         OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *         WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *     ====
 *
 *     Copyright (c) 2012 Alessandro F. Leite
 *
 *     Permission is hereby granted, free of charge, to any person obtaining
 *     a copy of this software and associated documentation files (the
 *     "Software"), to deal in the Software without restriction, including
 *     without limitation the rights to use, copy, modify, merge, publish,
 *     distribute, sublicense, and/or sell copies of the Software, and to
 *     permit persons to whom the Software is furnished to do so, subject to
 *     the following conditions:
 *
 *     The above copyright notice and this permission notice shall be
 *     included in all copies or substantial portions of the Software.
 *
 *     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *     EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *     MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *     NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *     LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *     OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *     WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * ====
 *
 * Copyright (c) 2012 Alessandro F. Leite
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.cloudfs;

import java.io.Serializable;

import com.google.common.base.Preconditions;

/**
 * <p>
 * Just two strings -- a "key" and a "secret". Used by OAuth in several places
 * (consumer key/secret, request token/secret, access token/secret). Use
 * specific subclasses instead of using this class directly.
 * </p>
 */
public abstract class TokenPair implements Serializable {

	/**
	 * Serial code version <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -2368944598457521651L;

	/**
	 * The "key" portion of the pair. For example, the "consumer key",
	 * "request token", or "access token". Will never contain the "|" character.
	 */
	private final String key;

	/**
	 * The "secret" portion of the pair. For example, the "consumer secret",
	 * "request token secret", or "access token secret".
	 */
	private final String secret;

	/**
	 * @param key
	 *            assigned to {@link #key}.
	 * @param secret
	 *            assigned to {@link #secret}.
	 * 
	 * @throws IllegalArgumentException
	 *             if key or secret is null or invalid.
	 */
	public TokenPair(String key, String secret) {
		Preconditions
				.checkArgument(
						!Preconditions.checkNotNull(key,
								"'key' must be non-null").contains("|"),
						String.format(
								"'key' must not constain a \"|\" character \"%s\"",
								key));
		this.key = key;
		this.secret = Preconditions.checkNotNull(secret,
				"'secret' must be non-null");
	}

	@Override
	public int hashCode() {
		return key.hashCode() ^ (secret.hashCode() << 1);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (!(obj instanceof TokenPair))
			return false;

		TokenPair other = (TokenPair) obj;
		return this.key.equals(other.key) && secret.equals(other.secret);
	}

	@Override
	public String toString() {
		return "{key=\"" + key + "\", secret=\"" + secret + "\"}";
	}

	public String key() {
		return this.key;
	}

	public String secret() {
		return this.secret;
	}
}