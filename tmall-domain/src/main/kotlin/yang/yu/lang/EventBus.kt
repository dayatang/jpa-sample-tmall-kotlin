package yang.yu.lang

/**
 * Event Bus. Accept events of any type and transmit them to the observers
 */
interface EventBus {

   /**
    * Post an event to the bus
    * @param event The event to post
    */
   fun post(event: Any)

   /**
    * Create an Observable of type T
    * @param eventType The class of the event to be observed
    * @return An Observable of type T
    */
   fun <T: Any> subscribe(eventType: Class<T>, consumer: (T) -> Unit)

   fun unsubscribe(eventType: Class<*>)
}
