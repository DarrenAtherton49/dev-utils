#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}
#end

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import dev.darrenatherton.growthjournal.R
import dev.darrenatherton.growthjournal.presentation.base.BaseFragment
import dev.darrenatherton.growthjournal.presentation.main.MainViewEffect
import dev.darrenatherton.growthjournal.presentation.main.MainViewModel
import dev.darrenatherton.growthjournal.presentation.main.MainViewModelFactory
import dev.darrenatherton.growthjournal.presentation.util.extension.getActivityViewModel
import dev.darrenatherton.growthjournal.presentation.util.extension.getMainComponent
import dev.darrenatherton.growthjournal.presentation.util.extension.getViewModel
import dev.darrenatherton.growthjournal.presentation.util.groupie.ViewHolder
import dev.darrenatherton.growthjournal.presentation.util.toolbar.ToolbarOptions
import javax.inject.Inject
import javax.inject.Named

#parse("File Header.java")
class ${NAME} : BaseFragment<${MVI_TYPE}Action, ${MVI_TYPE}State, ${MVI_TYPE}ViewEffect, ${MVI_TYPE}ViewModel>() {

    override val layoutResId: Int = R.layout.fragment_${LAYOUT_AND_STATE_IDENTIFIER}
    override val stateBundleKey: String = "bundle_key_${LAYOUT_AND_STATE_IDENTIFIER}_state"
    
    @Inject @field:Named(MainViewModelFactory.NAME)
    lateinit var mainVmFactory: ViewModelProvider.Factory

    @Inject @field:Named(${MVI_TYPE}ViewModelFactory.NAME)
    lateinit var vmFactory: ViewModelProvider.Factory
    
    override val sharedViewModel: MainViewModel by lazy { getActivityViewModel<MainViewModel>(mainVmFactory) }
    override val viewModel: ${MVI_TYPE}ViewModel by lazy { getViewModel<${MVI_TYPE}ViewModel>(vmFactory) }

    override val toolbarOptions: ToolbarOptions? = null
    
    @Inject lateinit var renderer: ${MVI_TYPE}Renderer
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }
    
    override fun onDestroyView() {
        recyclerView.adapter = null
        super.onDestroyView()
    }
    
    override fun onMenuItemClicked(menuItem: MenuItem): Boolean  = false
    
    override fun renderState(state: ${MVI_TYPE}State) {}
    
    override fun processViewEffects(viewEffect: ${MVI_TYPE}ViewEffect) {}

    override fun processSharedViewEffects(viewEffect: MainViewEffect) {}
    
    private fun initRecyclerView() {
        recyclerView.apply {
            setHasFixedSize(true)
            val layoutManager = LinearLayoutManager(context)
            setLayoutManager(layoutManager)
            adapter = GroupAdapter<ViewHolder>().apply {
                // todo add sections
            }
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