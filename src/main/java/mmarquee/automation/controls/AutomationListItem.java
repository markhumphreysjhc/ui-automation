/*
 * Copyright 2016-17 inpwtepydjuf@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mmarquee.automation.controls;

import mmarquee.automation.AutomationElement;
import mmarquee.automation.AutomationException;
import mmarquee.automation.ControlType;
import mmarquee.automation.pattern.Invoke;
import mmarquee.automation.pattern.PatternNotFoundException;
import mmarquee.automation.pattern.SelectionItem;

/**
 * Created by Mark Humphreys on 09/02/2016.
 *
 * Wrapper for the ListItem element.
 */
public class AutomationListItem extends AutomationBase implements Selectable, Clickable {

    SelectionItem selectItemPattern;
    Invoke invokePattern = null;

    /**
     * Constructor for the AutomationListItem
     * @param element The underlying automation element
     * @throws AutomationException Automation library error
     * @throws PatternNotFoundException Expected pattern not found
     */
    public AutomationListItem(AutomationElement element) throws PatternNotFoundException, AutomationException {
        super(element);
    }

    /**
     * Selects this item.
     * @throws AutomationException Something has gone wrong
     */
    public void select() throws AutomationException, PatternNotFoundException {
        if (this.selectItemPattern == null) {
            this.selectItemPattern = this.getSelectItemPattern();
        }

        this.selectItemPattern.select();
    }

    /**
     * Is this item selected?
     * @return True if selected
     * @throws AutomationException Automation library issue
     */
    public boolean isSelected() throws AutomationException, PatternNotFoundException {
        if (this.selectItemPattern == null) {
            this.selectItemPattern = this.getSelectItemPattern();
        }

        return this.selectItemPattern.isSelected();
    }

    /**
     * Clicks the item
     * 
     * @throws AutomationException Error in the automation library
     * @throws PatternNotFoundException Could not find the invoke pattern
     */
    public void click() throws AutomationException, PatternNotFoundException {
        if (this.invokePattern == null) {
            this.invokePattern = this.getInvokePattern();
        }

        if (this.isInvokePatternAvailable()) {
            this.invokePattern.invoke();
        } else {
            throw new PatternNotFoundException();
        }
    }

}
