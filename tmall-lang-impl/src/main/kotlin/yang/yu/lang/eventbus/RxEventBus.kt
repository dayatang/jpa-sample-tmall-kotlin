package yang.yu.lang.eventbus

import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import yang.yu.lang.EventBus
import java.util.function.Consumer

class RxEventBus : EventBus {

    private val bus = PublishSubject.create<Any>().toSerialized()

    private val disposables: MutableMap<String, MutableList<Disposable>> = HashMap()

    override fun post(event: Any) {
        bus.onNext(event)
    }

    override fun <T : Any> subscribe(eventType: Class<T>, consumer: (T) -> Unit) {
        val disposable = bus.ofType(eventType).subscribe(consumer::invoke)
        disposables.computeIfAbsent(eventType.name) { ArrayList() }
            .add(disposable)
    }

    override fun unsubscribe(eventType: Class<*>) {
        disposables[eventType.name]!!
            .forEach(Consumer { obj: Disposable -> obj.dispose() })
    }
}
