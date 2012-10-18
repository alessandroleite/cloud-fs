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
package org.cloudfs.io;

import java.io.FileFilter;

public interface File extends Comparable<File> {

	/**
	 * Return the length of the content of this {@link File} if it's exists in
	 * the file system.
	 * 
	 * @return the length of the content of this {@link File} if it's exists in
	 *         the file system.
	 */
	long size();

	/**
	 * Return the reference to the parent of this {@link File}. It will be
	 * <code>null</code> if the file is the root (/).
	 * 
	 * @return the reference to the parent of this {@link File}. It will be
	 *         <code>null</code> if the file is the root (/).
	 */
	File parent();

	/**
	 * 
	 * @return
	 */
	String name();

	/**
	 * 
	 * @return
	 */
	String absolutePath();

	/**
	 * 
	 * @return
	 */
	boolean createNew();
	
	/**
	 * 
	 * @return
	 */
	File[] list();

	/**
	 * 
	 * @param filter
	 * @return
	 */
	File[] list(FileFilter filter);

	/**
	 * 
	 * @return
	 * @throws IOException throws in case of any failure.
	 */
	public byte[] data() throws IOException;

	/**
	 * Delete this {@link File} of the file system.
	 * 
	 * @return <code>true</code> if succeed, <code>false</code> otherwise.
	 * @throws IOException throws if case of any failure.
	 */
	public boolean delete() throws IOException;

	/**
	 * 
	 * @param file
	 * @return
	 */
	boolean moveTo(File file);

	/**
	 * Rename a {@link File}.
	 * 
	 * @param name
	 *            the new name to be assigned to this {@link File}. May not be
	 *            <code>null</code> or blank.
	 * @return <code>true</code> if successes, <code>false</code> otherwise.
	 * @throws IOException throws in case of any failure.
	 */
	boolean rename(String name) throws IOException;

	/**
	 * Return <code>true</code> if this {@link File} exists in the file system
	 * or <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if this {@link File} exists in the file system
	 *         or <code>false</code> otherwise.
	 */
	boolean exists();

	/**
	 * 
	 * @return
	 */
	boolean isDirectory();

	/**
	 * 
	 * @return
	 */
	boolean isFile();

	/**
	 * Return <code>true</code> if this is a hidden file or <code>false</code>
	 * otherwise.
	 * 
	 * @return <code>true</code> if this is a hidden file, <code>false</code>
	 *         otherwise.
	 */
	boolean isHidden();
}