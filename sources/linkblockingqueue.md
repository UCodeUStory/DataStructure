### LinkBlockingQueue 原理

LinkBlockingQueue 内部使用了互斥锁ReentrantLock来实现，通过Condition来实现阻塞通知


通过观察源码


     public E take() throws InterruptedException {
            E x;
            int c = -1;
            final AtomicInteger count = this.count;
            final ReentrantLock takeLock = this.takeLock;
            takeLock.lockInterruptibly();
            try {
                while (count.get() == 0) {
                    notEmpty.await();
                }
                //添加数据
                x = dequeue();
                //原子化-1，
                c = count.getAndDecrement();
                if (c > 1)
                    notEmpty.signal();
            } finally {
                takeLock.unlock();
            }
            // 如果上一次是capacity，证明put线程阻塞，所以要唤醒
            if (c == capacity)
                signalNotFull();
            return x;
        }
        

        
     public void put(E e) throws InterruptedException {
             if (e == null) throw new NullPointerException();
             // Note: convention in all put/take/etc is to preset local var
             // holding count negative to indicate failure unless set.
             int c = -1;
             Node<E> node = new Node<E>(e);
             final ReentrantLock putLock = this.putLock;
             final AtomicInteger count = this.count;
             putLock.lockInterruptibly();
             try {
                 /*
                  * Note that count is used in wait guard even though it is
                  * not protected by lock. This works because count can
                  * only decrease at this point (all other puts are shut
                  * out by lock), and we (or some other waiting put) are
                  * signalled if it ever changes from capacity. Similarly
                  * for all other uses of count in other wait guards.
                  */
                 while (count.get() == capacity) {
                     notFull.await();
                 }
                 // 当我们条件通过时我们会添加新数据，
                 enqueue(node);
                 // 添加完后此时如何有数据被其他的线程取出，下面方法会保证count的一致性，
                 // 比如原本是1，被取出一个，这个时候就是0，再加1结果就是1
                 c = count.getAndIncrement();
                 // c 是返回原来的数量，所以+1是本次数量
                 if (c + 1 < capacity)
                     notFull.signal();
             } finally {
                 putLock.unlock();
             }
             // 如果上一次是0，证明take线程阻塞，所以要唤醒
             if (c == 0)
                 signalNotEmpty();
         }