/**
 * 
 */
package com.hexanome.controller.states;

/**
 * @author antitoine
 * \todo TODO
 */
public class PlanningSelectState extends DefaultState {

    private static PlanningSelectState planningSelectState = null;

    private PlanningSelectState(){
        // Nothing to do here
    }

    /**
     * Returns the instance of the PlanningSelectState,
     * it is a singleton
     * @return The instance of PlanningSelectState
     */
    public static PlanningSelectState getInstance() {
        if(planningSelectState == null)
        {
            planningSelectState = new PlanningSelectState();
        }
        return planningSelectState;
    }

    /* (non-Javadoc)
     * @see com.hexanome.controller.states.IState#btnCancel()
     */
    @Override
    public void btnCancel() {
        // \todo TODO
    }

}
