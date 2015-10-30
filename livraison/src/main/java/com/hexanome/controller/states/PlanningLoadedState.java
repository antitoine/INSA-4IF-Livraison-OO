/**
 * 
 */
package com.hexanome.controller.states;

/**
 * @author antitoine
 *
 */
public class PlanningLoadedState extends DefaultState {

    private static PlanningLoadedState planningLoadedState = null;

    private PlanningLoadedState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the PlanningLoadedState,
     * it is a singleton
     * @return The instance of PlanningLoadedState
     */
    public static PlanningLoadedState getInstance() {
        if(planningLoadedState == null)
        {
            planningLoadedState = new PlanningLoadedState();
        }
        return planningLoadedState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadMap()
     */
    @Override
    public void btnLoadMap() {
        // \todo TODO
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnLoadPlanning()
     */
    @Override
    public void btnLoadPlanning() {
        // \todo TODO
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCloseMap()
     */
    @Override
    public void btnCloseMap() {
        // \todo TODO
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnClearPlanning()
     */
    @Override
    public void btnClearPlanning() {
        // \todo TODO
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnGenerateRoute()
     */
    @Override
    public void btnGenerateRoute() {
        // \todo TODO
    }

}
