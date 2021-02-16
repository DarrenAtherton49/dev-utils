#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ww.roxie.Reducer
import io.reactivex.Observable.merge
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

#parse("File Header.java")
class ${NAME} @Inject constructor(
    initialState: ${MVI_TYPE}State?,
    private val schedulers: RxSchedulers
) : AppViewModel<${MVI_TYPE}Action, ${MVI_TYPE}State, ${MVI_TYPE}ViewEffect>() {

    override val initialState = initialState ?: ${MVI_TYPE}State()

    private val reducer: Reducer<${MVI_TYPE}State, ${MVI_TYPE}Change> = { previousState, change ->
        previousState
    }

    init {
        bindActions()
    }
    
    private fun bindActions() {
        val stateChanges = merge(
            loadDataChange,
            retryButtonChange
        )

        disposables += stateChanges
            .scan(initialState, reducer)
            .filter { it.loadingState != LoadingState.Idle }
            .distinctUntilChanged()
            .observeOn(schedulers.main)
            .subscribe(state::setValue, Timber::e)
    }
    
    @ViewScope
    class Factory(
        private val initialState: ${MVI_TYPE}State?,
        private val schedulers: RxSchedulers
    ) : ViewModelProvider.Factory {
    
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T  {
            return ${MVI_TYPE}ViewModel(
                initialState = initialState,
                schedulers = schedulers
            ) as T
        }
    
        companion object {
            const val NAME = "${MVI_TYPE}ViewModel.Factory"
        }
    }
}
