import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import service.CommandsService
import service.DeviceService
import service.EventsService

fun appModule() = module {
    single { DeviceService() }
    single { EventsService() }
    single { CommandsService() }
}

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}
