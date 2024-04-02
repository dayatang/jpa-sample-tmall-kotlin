package yang.yu.lang

import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import jakarta.inject.Named

/**
 * Event Bus. Accept events of any type and transmit them to the observers
 */
@Named
class RxEventBus: EventBus {

   private val bus = PublishSubject.create<Any>().toSerialized()

   private val observables = HashMap<String, Disposable>()

   /**
    * Post an event to the bus
    * @param event The event to post
    */
   override fun post(event: Any) {
      bus.onNext(event)
   }

   override fun <T : Any> subscribe(eventType: Class<T>, consumer: (T) -> Unit) {
      val observable = bus.ofType(eventType).subscribe(consumer::invoke)
      observables[eventType.name] = observable
   }

   override fun unsubscribe(eventType: Class<*>) {
      val observable = observables[eventType.name]
      observable?.dispose()
   }

}
