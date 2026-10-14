/*
 *  Copyright 2023 ihromant.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.teavm.classlib.java.util;

import org.teavm.classlib.impl.Base64Impl;

public final class TBase64 {
    private TBase64() {
    }

    public static Encoder getEncoder() {
        return new Encoder(Base64Impl.alphabet, true);
    }

    public static Encoder getUrlEncoder() {
        return new Encoder(Base64Impl.urlAlphabet, false);
    }

    public static class Encoder {
        private final byte[] mapping;
        private final boolean padding;
        private final int line;
        private final byte[] sep;

        private Encoder(byte[] mapping, boolean padding) {
            this(mapping, padding, 0, null);
        }

        private Encoder(byte[] mapping, boolean padding, int line, byte[] sep) {
            this.mapping = mapping;
            this.padding = padding;
            this.line = line;
            this.sep = sep;
        }

        public byte[] encode(byte[] src) {
            var r = Base64Impl.encode(src, mapping, padding);
            if (line <= 0 || r.length <= line) return r;
            var o = new java.io.ByteArrayOutputStream();
            for (int i = 0; i < r.length; i += line) {
                if (i > 0) o.write(sep, 0, sep.length);
                o.write(r, i, Math.min(line, r.length - i));
            }
            return o.toByteArray();
        }

        public String encodeToString(byte[] src) {
            var bytes = encode(src);
            var chars = new char[bytes.length];
            for (var i = 0; i < bytes.length; ++i) {
                chars[i] = (char) (bytes[i] & 0xFF);
            }
            return new String(chars);
        }

        public Encoder withoutPadding() {
            return new Encoder(mapping, false, line, sep);
        }
    }

    public static Decoder getDecoder() {
        return new Decoder(Base64Impl.reverse);
    }

    public static Decoder getUrlDecoder() {
        return new Decoder(Base64Impl.urlReverse);
    }

    public static Decoder getMimeDecoder() {
        return new Decoder(Base64Impl.reverse, true);
    }

    public static Encoder getMimeEncoder() {
        return new Encoder(Base64Impl.alphabet, true, 76, new byte[]{'\r', '\n'});
    }

    public static Encoder getMimeEncoder(int l, byte[] sep) {
        return new Encoder(Base64Impl.alphabet, true, l / 4 * 4, sep);
    }

    public static class Decoder {
        private final int[] mapping;
        private final boolean mime;

        private Decoder(int[] mapping) {
            this(mapping, false);
        }

        private Decoder(int[] mapping, boolean mime) {
            this.mapping = mapping;
            this.mime = mime;
        }

        public byte[] decode(byte[] src) {
            if (mime) {
                var o = new java.io.ByteArrayOutputStream();
                for (byte b : src) {
                    int c = b & 0xff;
                    if (c == '=' || (c < mapping.length && mapping[c] >= 0)) o.write(b);
                }
                src = o.toByteArray();
            }
            return Base64Impl.decode(src, mapping);
        }

        public byte[] decode(String src) {
            var bytes = new byte[src.length()];
            for (var i = 0; i < bytes.length; ++i) {
                bytes[i] = (byte) src.charAt(i);
            }
            return decode(bytes);
        }
    }
}
