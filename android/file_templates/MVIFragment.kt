#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import javax.inject.Inject
import javax.inject.Named

#parse("File Header.java")
class ${NAME} : BaseFragment<${MVI_TYPE}Action, ${MVI_TYPE}State, ${MVI_TYPE}ViewEffect, ${MVI_TYPE}ViewModel>() {

    override val layoutResId: Int = R.layout.fragment_${LAYOUT_AND_STATE_IDENTIFIER}
    override val stateBundleKey: String = "bundle_key_${LAYOUT_AND_STATE_IDENTIFIER}_state"
   
    @Inject @field:Named(${MVI_TYPE}ViewModel.Factory.NAME)
    lateinit var vmFactory: ViewModelProvider.Factory
    
    override val viewModel by viewModels<${MVI_TYPE}ViewModel> { vmFactory }
    
    override val toolbarOptions: ToolbarOptions? = null
    
    @Inject lateinit var renderer: ${MVI_TYPE}Renderer
    
    private val groupAdapter: GroupAdapter<ViewHolder> = GroupAdapter()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }
    
    override fun onDestroyView() {
        recyclerView.adapter = null
        super.onDestroyView()
    }
        
    override fun renderState(state: ${MVI_TYPE}State) {}
    
    override fun processViewEffects(viewEffect: ${MVI_TYPE}ViewEffect) {}
    
    private fun initRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            setLayoutManager(layoutManager)
            adapter = groupAdapter
        }
    }
    
    override fun initInjection(initialState: ${MVI_TYPE}State?) {
        Dagger${MVI_TYPE}Component
            .factory()
            .create(getMainComponent(), initialState)
            .inject(this)
    }

    companion object {
        fun newInstance(): ${NAME} = ${NAME}()
    }
}
