package yang.yu.lang

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * Event Bus. Accept events of any type and transmit them to the observers
 * 事件总线。接受任何类型的事件，并将它们发送给各自的监听者
 */
object EventBus {

   private val bus = PublishSubject.create<Any>().toSerialized()

   /**
    * Post an event to the bus
    * 发布事件到事件总线
    *
    * @param event The event to post
    */
   fun post(event: Any) {
      bus.onNext(event)
   }

   /**
    * Create an Observable of type T
    * 创建特定事件类型的事件源
    *
    * @param eventType The class of the event to be observed
    * @return An Observable of type T
    */
   fun <T: Any> toObservable(eventType: Class<T>): Observable<T> {
      return bus.ofType(eventType)
   }
}