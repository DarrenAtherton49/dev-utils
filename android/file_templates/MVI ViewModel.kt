#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ww.roxie.BaseAction
import com.ww.roxie.BaseState
import com.ww.roxie.Reducer
import dev.darrenatherton.growthjournal.presentation.base.AppViewModel
import dev.darrenatherton.growthjournal.presentation.base.BaseViewEffect
import dev.darrenatherton.growthjournal.util.injection.ViewScope
import dev.darrenatherton.growthjournal.util.threading.RxSchedulers
import io.reactivex.Observable.merge
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.parcel.Parcelize
import timber.log.Timber
import javax.inject.Inject

#parse("File Header.java")
class ${NAME} @Inject constructor(
    initialState: ${MVI_TYPE}State?,
    private val schedulers: RxSchedulers
) : AppViewModel<${MVI_TYPE}Action, ${MVI_TYPE}State, ${MVI_TYPE}ViewEffect>() {

    override val initialState = initialState ?: ${MVI_TYPE}State.Idle

    private val reducer: Reducer<${MVI_TYPE}State, ${MVI_TYPE}Change> = { oldState, change ->
        ${MVI_TYPE}State.Loading
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
            .filter { it !is ${MVI_TYPE}State.Idle }
            .distinctUntilChanged()
            .observeOn(schedulers.main)
            .subscribe(state::setValue, Timber::e)
    }
}

//================================================================================
// MVI
//================================================================================

sealed class ${MVI_TYPE}Action : BaseAction {
    object Load : ${MVI_TYPE}Action()
}

sealed class ${MVI_TYPE}Change {
    object Loading : ${MVI_TYPE}Change()
    data class Result(val string: String) : ${MVI_TYPE}Change()
}

sealed class ${MVI_TYPE}State : BaseState, Parcelable {

    @Parcelize
    object Idle : ${MVI_TYPE}State()

    @Parcelize
    object Loading: ${MVI_TYPE}State()

    @Parcelize
    data class Content(val string: String) : ${MVI_TYPE}State()

    @Parcelize
    data class Error(val message: String) : ${MVI_TYPE}State()
}

sealed class ${MVI_TYPE}ViewEffect : BaseViewEffect

//================================================================================
// Factory
//================================================================================

@ViewScope
class ${MVI_TYPE}ViewModelFactory(
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
        const val NAME = "${MVI_TYPE}ViewModelFactory"
    }
}