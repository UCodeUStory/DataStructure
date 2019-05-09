### Condition的作用是对锁进行更精确的控制。

- Condition中的await()方法相当于Object的wait()方法，Condition中的signal()方法相当于Object的notify()方法，
  Condition中的signalAll()相当于Object的notifyAll()方法。不同的是，Object中的wait(),notify(),notifyAll()方法是和"同步锁"(synchronized关键字)捆绑使用的；而Condition是需要与"互斥锁"/"共享锁"捆绑使用的。tion中的signal()方法相当于Object的notify()方法，Condition中的signalAll()相当于Object的notifyAll()方法。不同的是，Object中的wait(),notify(),notifyAll()方法是和"同步锁"(synchronized关键字)捆绑使用的；而Condition是需要与"互斥锁"/"共享锁"捆绑使用的。

- Condition除了支持上面的功能之外，它更强大的地方在于：能够更加精细的控制多线程的休眠与唤醒。对于同一个锁，我们可以创建多个Condition，在不同的情况下使用不同的Condition。
  例如，假如多线程读/写同一个缓冲区：当向缓冲区中写入数据之后，唤醒"读线程"；当从缓冲区读出数据之后，唤醒"写线程"；并且当缓冲区满的时候，"写线程"需要等待；当缓冲区为空时，"读线程"需要等待。         如果采用Object类中的wait(), notify(), notifyAll()实现该缓冲区，当向缓冲区写入数据之后需要唤醒"读线程"时，不可能通过notify()或notifyAll()明确的指定唤醒"读线程"，而只能通过notifyAll唤醒所有线程(但是notifyAll无法区分唤醒的线程是读线程，还是写线程)。  但是，通过Condition，就能明确的指定唤醒读线程。
  看看下面的示例3，可能对这个概念有更深刻的理解。
  
  
  
  
      internal class BoundedBuffer {
          val lock: Lock = ReentrantLock()
          val notFull = lock.newCondition()
          val notEmpty = lock.newCondition()
      
          val items = arrayOfNulls<Any>(5)
          var putptr: Int = 0
          var takeptr: Int = 0
          var count: Int = 0
      
          @Throws(InterruptedException::class)
          fun put(x: Any) {
              lock.lock()    //获取锁
              try {
                  // 如果“缓冲已满”，则等待；直到“缓冲”不是满的，才将x添加到缓冲中。
                  while (count == items.size)
                      notFull.await()
                  // 将x添加到缓冲中
                  items[putptr] = x
                  // 将“put统计数putptr+1”；如果“缓冲已满”，则设putptr为0。
                  if (++putptr == items.size) putptr = 0
                  // 将“缓冲”数量+1
                  ++count
                  // 唤醒take线程，因为take线程通过notEmpty.await()等待
                  notEmpty.signal()
      
                  // 打印写入的数据
                  println(Thread.currentThread().name + " put  " + x as Int)
              } finally {
                  lock.unlock()    // 释放锁
              }
          }
      
          @Throws(InterruptedException::class)
          fun take(): Any {
              lock.lock()    //获取锁
              try {
                  // 如果“缓冲为空”，则等待；直到“缓冲”不为空，才将x从缓冲中取出。
                  while (count == 0)
                      notEmpty.await()
                  // 将x从缓冲中取出
                  val x = items[takeptr]
                  // 将“take统计数takeptr+1”；如果“缓冲为空”，则设takeptr为0。
                  if (++takeptr == items.size) takeptr = 0
                  // 将“缓冲”数量-1
                  --count
                  // 唤醒put线程，因为put线程通过notFull.await()等待
                  notFull.signal()
      
                  // 打印取出的数据
                  println(Thread.currentThread().name + " take " + x as Int)
                  return x
              } finally {
                  lock.unlock()    // 释放锁
              }
          }
      }
