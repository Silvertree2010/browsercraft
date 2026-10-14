/*
 *  Copyright 2017 Alexey Andreev.
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

import java.util.Collection;
import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class TSpliterators {

    private TSpliterators() {
    }

    public static <T> TSpliterator<T> spliterator(Object[] array, int additionalCharacteristics) {
        return new TSpliterator<T>() {
            private int index;

            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                if (index >= array.length) {
                    return false;
                }

                @SuppressWarnings("unchecked")
                T e = (T) array[index++];
                action.accept(e);
                return true;
            }

            @Override
            public TSpliterator<T> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return array.length - index;
            }

            @Override
            public int characteristics() {
                return additionalCharacteristics | TSpliterator.SIZED;
            }
        };
    }
    
    public static <T> TSpliterator<T> spliterator(Collection<? extends T> c, int characteristics) {
        return spliterator(c.iterator(), c.size(), characteristics);
    }

    public static TSpliterator.OfInt spliterator(int[] array, int additionalCharacteristics) {
        return spliterator(array, 0, array.length, additionalCharacteristics);
    }

    public static TSpliterator.OfInt spliterator(int[] array, int fromIndex, int toIndex,
            int additionalCharacteristics) {
        return new TSpliterator.OfInt() {
            int index = fromIndex;

            @Override
            public TSpliterator.OfInt trySplit() {
                return null;
            }

            @Override
            public boolean tryAdvance(IntConsumer action) {
                if (index < toIndex) {
                    action.accept(array[index++]);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public long estimateSize() {
                return toIndex - index;
            }

            @Override
            public int characteristics() {
                return TSpliterator.SIZED;
            }
        };
    }

    public static <T> TSpliterator<T> spliterator(Iterator<? extends T> iterator, long size, int characteristics) {
        return new TSpliterator<T>() {
            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                if (iterator.hasNext()) {
                    action.accept(iterator.next());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public TSpliterator<T> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return size;
            }

            @Override
            public int characteristics() {
                return characteristics;
            }
        };
    }

    public static <T> TSpliterator<T> spliteratorUnknownSize(Iterator<? extends T> iterator, int characteristics) {
        return new TSpliterator<T>() {
            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                if (iterator.hasNext()) {
                    action.accept(iterator.next());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public TSpliterator<T> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return Long.MAX_VALUE;
            }

            @Override
            public int characteristics() {
                return characteristics;
            }
        };
    }

    public static TSpliterator.OfInt spliterator(PrimitiveIterator.OfInt iterator, long size, int characteristics) {
        return new TSpliterator.OfInt() {
            @Override
            public boolean tryAdvance(IntConsumer action) {
                if (iterator.hasNext()) {
                    action.accept(iterator.next());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public OfInt trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return size;
            }

            @Override
            public int characteristics() {
                return characteristics;
            }
        };
    }

    public static TSpliterator.OfInt spliteratorUnknownSize(PrimitiveIterator.OfInt iterator, int characteristics) {
        return new TSpliterator.OfInt() {
            @Override
            public boolean tryAdvance(IntConsumer action) {
                if (iterator.hasNext()) {
                    action.accept(iterator.next());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public OfInt trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return Long.MAX_VALUE;
            }

            @Override
            public int characteristics() {
                return characteristics;
            }
        };
    }

    public static TSpliterator.OfLong spliterator(PrimitiveIterator.OfLong iterator, long size, int characteristics) {
        return new TSpliterator.OfLong() {
            @Override
            public boolean tryAdvance(LongConsumer action) {
                if (iterator.hasNext()) {
                    action.accept(iterator.next());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public OfLong trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return size;
            }

            @Override
            public int characteristics() {
                return characteristics;
            }
        };
    }

    public static TSpliterator.OfLong spliteratorUnknownSize(PrimitiveIterator.OfLong iterator, int characteristics) {
        return new TSpliterator.OfLong() {
            @Override
            public boolean tryAdvance(LongConsumer action) {
                if (iterator.hasNext()) {
                    action.accept(iterator.next());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public OfLong trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return Long.MAX_VALUE;
            }

            @Override
            public int characteristics() {
                return characteristics;
            }
        };
    }

    public static TSpliterator.OfDouble spliterator(PrimitiveIterator.OfDouble iterator, long size,
            int characteristics) {
        return new TSpliterator.OfDouble() {
            @Override
            public boolean tryAdvance(DoubleConsumer action) {
                if (iterator.hasNext()) {
                    action.accept(iterator.next());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public OfDouble trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return size;
            }

            @Override
            public int characteristics() {
                return characteristics;
            }
        };
    }

    public static TSpliterator.OfDouble spliteratorUnknownSize(PrimitiveIterator.OfDouble iterator,
            int characteristics) {
        return new TSpliterator.OfDouble() {
            @Override
            public boolean tryAdvance(DoubleConsumer action) {
                if (iterator.hasNext()) {
                    action.accept(iterator.next());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public OfDouble trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return Long.MAX_VALUE;
            }

            @Override
            public int characteristics() {
                return characteristics;
            }
        };
    }

    public static <T> java.util.Spliterator<T> emptySpliterator() { return java.util.List.<T>of().spliterator(); }
    public static java.util.Spliterator.OfInt emptyIntSpliterator() { return java.util.stream.IntStream.empty().spliterator(); }
    public static java.util.Spliterator.OfLong emptyLongSpliterator() { return java.util.stream.LongStream.empty().spliterator(); }
    public static java.util.Spliterator.OfDouble emptyDoubleSpliterator() { return java.util.stream.DoubleStream.empty().spliterator(); }

    public static <T> java.util.Iterator<T> iterator(java.util.Spliterator<? extends T> s) {
        return new java.util.Iterator<T>() {
            boolean has;
            T next;
            public boolean hasNext() { if (!has) has = s.tryAdvance(x -> next = x); return has; }
            public T next() { if (!hasNext()) throw new java.util.NoSuchElementException(); has = false; T r = next; next = null; return r; }
        };
    }
    public static java.util.PrimitiveIterator.OfInt iterator(java.util.Spliterator.OfInt s) {
        return new java.util.PrimitiveIterator.OfInt() {
            boolean has;
            int next;
            public boolean hasNext() { if (!has) has = s.tryAdvance((java.util.function.IntConsumer) x -> next = x); return has; }
            public int nextInt() { if (!hasNext()) throw new java.util.NoSuchElementException(); has = false; return next; }
        };
    }
    public static java.util.PrimitiveIterator.OfLong iterator(java.util.Spliterator.OfLong s) {
        return new java.util.PrimitiveIterator.OfLong() {
            boolean has;
            long next;
            public boolean hasNext() { if (!has) has = s.tryAdvance((java.util.function.LongConsumer) x -> next = x); return has; }
            public long nextLong() { if (!hasNext()) throw new java.util.NoSuchElementException(); has = false; return next; }
        };
    }

    public abstract static class AbstractSpliterator<T> implements java.util.Spliterator<T> {
        long est;
        final int ch;
        protected AbstractSpliterator(long est, int ch) { this.est = est; this.ch = (ch & SIZED) != 0 ? ch | SUBSIZED : ch; }
        public java.util.Spliterator<T> trySplit() { return null; }
        public long estimateSize() { return est; }
        public int characteristics() { return ch; }
    }
    public abstract static class AbstractIntSpliterator implements java.util.Spliterator.OfInt {
        long est;
        final int ch;
        protected AbstractIntSpliterator(long est, int ch) { this.est = est; this.ch = ch; }
        public java.util.Spliterator.OfInt trySplit() { return null; }
        public long estimateSize() { return est; }
        public int characteristics() { return ch; }
    }
    public abstract static class AbstractLongSpliterator implements java.util.Spliterator.OfLong {
        long est;
        final int ch;
        protected AbstractLongSpliterator(long est, int ch) { this.est = est; this.ch = ch; }
        public java.util.Spliterator.OfLong trySplit() { return null; }
        public long estimateSize() { return est; }
        public int characteristics() { return ch; }
    }
}
