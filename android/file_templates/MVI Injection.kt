#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dev.darrenatherton.growthjournal.presentation.main.MainComponent
import dev.darrenatherton.growthjournal.util.injection.ViewScope
import dev.darrenatherton.growthjournal.util.threading.RxSchedulers
import javax.inject.Named

@ViewScope
@Component(
    dependencies = [MainComponent::class],
    modules = [${MVI_TYPE}Module::class]
)
interface ${MVI_TYPE}Component {

    fun inject(fragment: ${MVI_TYPE}Fragment)

    @Component.Factory
    interface Factory {
        fun create(
            mainComponent: MainComponent,
            @BindsInstance initialState: ${MVI_TYPE}State?
        ): ${MVI_TYPE}Component
    }
}


@Module
object ${MVI_TYPE}Module {

    @JvmStatic
    @Provides
    @ViewScope
    internal fun provideRenderer(context: Context): ${MVI_TYPE}Renderer = ${MVI_TYPE}Renderer(context)
    
    @JvmStatic
    @Provides
    @Named(${MVI_TYPE}ViewModelFactory.NAME)
    @ViewScope
    internal fun provideViewModelFactory(
        initialState: ${MVI_TYPE}State?,
        schedulers: RxSchedulers
    ): ViewModelProvider.Factory {
        return ${MVI_TYPE}ViewModelFactory(
            initialState = initialState,
            schedulers = schedulers
        )
    }
}