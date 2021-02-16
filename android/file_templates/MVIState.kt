#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end

import android.os.Parcelable
import com.ww.roxie.BaseState
import kotlinx.android.parcel.Parcelize

#parse("File Header.java")
@Parcelize
data class ${MVI_TYPE}State(
    val loadingState: LoadingState = LoadingState.Idle
) : BaseState, Parcelable
