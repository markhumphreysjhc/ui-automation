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
package mmarquee.automation;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import mmarquee.automation.uiautomation.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark Humphreys on 06/03/2016.
 * <p>
 * Wrapper for the underlying automation element.
 * </p>
 */
public class AutomationElement extends BaseAutomation {
    /**
     * <p>
     * The underlying automation element
     * </p>
     */
    public IUIAutomationElement3 element;

    /**
     * Constructor of AutomationElement
     *
     * @param element The element
     */
    public AutomationElement(IUIAutomationElement3 element) {
        this.element = element;
    }

    /**
     * Gets the property associated with the passed in id
     *
     * @param propertyId The property ID to get
     * @return The property ID
     * @throws AutomationException Call to Automation API failed
     */
    public Object getPropertyValue(int propertyId) throws AutomationException {
        Variant.VARIANT.ByReference value = new Variant.VARIANT.ByReference();

        if (this.element.getCurrentPropertyValue(propertyId, value) != 0) {
            throw new AutomationException();
        }

        return value.getValue();
    }

    /**
     * Gets the current control type
     *
     * @return The current control type
     * @throws AutomationException Call to Automation API failed
     */
    public int getControlType() throws AutomationException {
        IntByReference ibr = new IntByReference();

        if (this.element.getCurrentControlType(ibr) != 0) {
            throw new AutomationException();
        }

        return ibr.getValue();
    }

    /**
     * Gets the current class name of the element
     *
     * @return The current class name
     * @throws AutomationException Call to Automation API failed
     */
    public String getClassName() throws AutomationException {
        PointerByReference sr = new PointerByReference();

        if (this.element.getCurrentClassName(sr) != 0) {
            throw new AutomationException();
        }

        return sr.getValue().getWideString(0);
    }

    /**
     * Gets the current automation id of the element
     *
     * @return The current automation id
     * @throws AutomationException Call to Automation API failed
     */
    public String getAutomationId() throws AutomationException {
        PointerByReference sr = new PointerByReference();

        if (this.element.getCurrentAutomationId(sr) != 0) {
            throw new AutomationException();
        }

        return sr.getValue().getWideString(0);
    }

    /**
     * Gets the current localized control type of the element
     *
     * @return The current class name
     * @throws AutomationException Call to Automation API failed
     */
    public String localizedControlType() throws AutomationException {
        PointerByReference sr = new PointerByReference();

        if (this.element.getCurrentLocalizedControlType(sr) != 0) {
            throw new AutomationException();
        }

        return sr.getValue().getWideString(0);
    }

    /**
     * Gets the current IsPassword value.
     *
     * @return True if IsPassword
     * @throws AutomationException Call to Automation API failed
     */
    public Boolean isPassword() throws AutomationException {
        IntByReference ibr = new IntByReference();

        if (this.element.getCurrentIsPassword(ibr) != 0) {
            throw new AutomationException();
        }

        return ibr.getValue() == 1;
    }

    /**
     * Returns whether the element is off screen
     * @return True if off screen
     * @throws AutomationException Call to Automation API failed
     */
    public WinDef.BOOL offScreen() throws AutomationException {
        WinDef.BOOLByReference bbr = new WinDef.BOOLByReference();

        if (this.element.getCurrentIsOffscreen(bbr) != 0) {
            throw new AutomationException();
        }

        return bbr.getValue();
    }

    /**
     * Returns whether the element is a content element
     * @return True if content element
     * @throws AutomationException Call to Automation API failed
     */
    public WinDef.BOOL isContentElement() throws AutomationException {
        WinDef.BOOLByReference bbr = new WinDef.BOOLByReference();

        if (this.element.getCurrentIsContentElement(bbr) != 0) {
            throw new AutomationException();
        }

        return bbr.getValue();
    }

    /**
     * Returns whether the element is a control element
     * @return True if control element
     * @throws AutomationException Call to Automation API failed
     */
    public WinDef.BOOL isControlElement() throws AutomationException {
        WinDef.BOOLByReference bbr = new WinDef.BOOLByReference();

        if (this.element.getCurrentIsControlElement(bbr) != 0) {
            throw new AutomationException();
        }

        return bbr.getValue();
    }

    /**
     * Returns whether the element is enabled
     * @return True if enabled
     * @throws AutomationException Call to Automation API failed
     */
    public WinDef.BOOL isEnabled() throws AutomationException {
        WinDef.BOOLByReference bbr = new WinDef.BOOLByReference();

        if (this.element.getCurrentIsEnabled(bbr)  != 0) {
            throw new AutomationException();
        }

        return bbr.getValue();
    }

    /**
     * Gets the name, either from the current ot cache property
     *
     * @return The name (either cached or current)
     * @throws AutomationException Call to Automation API failed
     */
    public String getName() throws AutomationException {
        return this.currentName();
    }

    protected Logger logger = Logger.getLogger(AutomationElement.class.getName());

    /**
     * Gets the current name of the control
     *
     * @return The name of the element
     * @throws AutomationException Call to Automation API failed
     */
    protected String currentName() throws AutomationException {
        PointerByReference sr = new PointerByReference();

        if (this.element.getCurrentName(sr) != 0) {
            throw new AutomationException();
        }

        if (sr.getValue() == null) {
            return "";
        } else {
            return sr.getValue().getWideString(0);
        }
    }

//    /**
//     * Sets the name of the element
//     * @param name The name to use
//     */
//    public void setName(String name) {
//        this.element.setName(name);
// Not sure how this worked
//    }

    /**
     * Finds the first element that matches the raw condition
     *
     * @param scope      Tree scope
     * @param pCondition The raw condition
     * @return The first matching element
     * @throws AutomationException Something has gone wrong
     */
    public AutomationElement findFirst(TreeScope scope, PointerByReference pCondition) throws AutomationException {
        PointerByReference pbr = new PointerByReference();

        this.element.findFirst(scope, pCondition.getValue(), pbr);

        try {
            IUIAutomationElement3 element = getAutomationElementFromReference(pbr);
            return new AutomationElement(element);
        } catch (NullPointerException npe) {
            throw new ElementNotFoundException();
        }
    }

    /**
     * Get the current pattern that matches the patternId
     *
     * @param patternId What pattern to get
     * @return The pattern
     * @throws AutomationException Call to Automation API failed
     */
    public PointerByReference getPattern(int patternId) throws AutomationException {
        PointerByReference pbr = new PointerByReference();

        if (this.element.getCurrentPattern(patternId, pbr) != 0) {
            throw new AutomationException();
        }

        return pbr;
    }

    /**
     * Sets focus to the element
     */
    public void setFocus() {
        this.element.setFocus();
    }

    /**
     * Gets all of the descendant elements that match the condition
     *
     * @param pCondition The condition
     * @return List of matching elements
     * @throws AutomationException Call to Automation API failed
     */
    public List<AutomationElement> findAllDescendants(Pointer pCondition) throws AutomationException {
        return this.findAll(new TreeScope(TreeScope.Descendants), pCondition);
    }

    /**
     * Gets all of the elements that match the condition and scope
     *
     * @param scope The scope in the element tree
     * @param pCondition The condition
     * @return List of matching elements
     * @throws AutomationException Call to Automation API failed
     */
    public List<AutomationElement> findAll(TreeScope scope, Pointer pCondition) throws AutomationException {

        List<AutomationElement> items = new ArrayList<AutomationElement>();

        PointerByReference pAll = new PointerByReference();

        if (this.element.findAll(scope, pCondition, pAll) != 0) {
            throw new AutomationException();
        }

        IUIAutomationElementArray collection = getAutomationElementArrayFromReference(pAll);

        IntByReference ibr = new IntByReference();

        collection.getLength(ibr);

        int counter = ibr.getValue();

        for (int a = 0; a < counter; a++) {
            PointerByReference pbr = new PointerByReference();

            collection.getElement(a, pbr);

            IUIAutomationElement3 element = getAutomationElementFromReference(pbr);

            items.add(new AutomationElement(element));

        }

        return items;
    }

    /**
     * Gets the current ARIA role
     *
     * @return String representing the ARIA role
     * @throws AutomationException Call to Automation API failed
     */
    public String getAriaRole() throws AutomationException {
        PointerByReference sr = new PointerByReference();

        if (this.element.getCurrentAriaRole(sr) != 0) {
            throw new AutomationException();
        }

        return sr.getValue().getWideString(0);
    }

    /**
     * Gets the current orientation
     *
     * @return The orientation
     * @throws AutomationException Something has gone wrong
     */
    public OrientationType getOrientation() throws AutomationException {
        IntByReference ibr = new IntByReference();

        if (this.element.getCurrentOrientation(ibr) != 0) {
            throw new AutomationException();
        }

        return OrientationType.fromInt(ibr.getValue());
    }

    /**
     * Gets the framework ID
     *
     * @return The framework ID
     * @throws AutomationException Call to Automation API failed
     */
    public String getFrameworkId() throws AutomationException {
        PointerByReference sr = new PointerByReference();

        if (this.element.getCurrentFrameworkId(sr) != 0) {
            throw new AutomationException();
        }

        return sr.getValue().getWideString(0);
    }

    /**
     * Gets the provider description
     *
     * @return The provider description
     * @throws AutomationException Call to Automation API failed
     */
    public String getProviderDescription() throws AutomationException {
        PointerByReference sr = new PointerByReference();

        if (this.element.getCurrentProviderDescription(sr) != 0) {
            throw new AutomationException();
        }

        return sr.getValue().getWideString(0);
    }

//    /**
//     * Get the runtime Id
//     * @return The runtime ID
//     */
//    //   public int[] getRuntimeId() {
//   //       return element.getRuntimeId();
//    //   }

    /**
     * Gets the process ID
     *
     * @return The process ID
     * @throws AutomationException Call to Automation API failed
     */
    public Integer getProcessId() throws AutomationException {
        IntByReference ibr = new IntByReference();

        if (this.element.getCurrentProcessId(ibr) != 0) {
            throw new AutomationException();
        }

        return ibr.getValue();
    }

    /**
     * Gets the current item status
     *
     * @return The status
     * @throws AutomationException Call to Automation API failed
     */
    public String getItemStatus() throws AutomationException {
        PointerByReference sr = new PointerByReference();

        if (this.element.getCurrentItemStatus(sr) != 0) {
            throw new AutomationException();
        }

        return sr.getValue().getWideString(0);
    }

    /**
     * Gets the current accelerator key associated with the element
     *
     * @return The accelerator key
     * @throws AutomationException Call to Automation API failed
     */
    public String getAcceleratorKey() throws AutomationException {
        PointerByReference sr = new PointerByReference();

        if (this.element.getCurrentAcceleratorKey(sr) != 0) {
            throw new AutomationException();
        }

        return sr.getValue().getWideString(0);
    }

    /**
     * Gets the clickable point for the control
     * @return The clickable point
     * @throws AutomationException Call to Automation API failed
     */
    public WinDef.POINT getClickablePoint() throws AutomationException {
        WinDef.POINT.ByReference pbr = new WinDef.POINT.ByReference();

        WinDef.BOOLByReference br = new WinDef.BOOLByReference();

        if (this.element.getClickablePoint(pbr, br) != 0) {
            throw new AutomationException();
        }

        return new WinDef.POINT(pbr.x, pbr.y);
    }

    /**
     * Gets the bounding rectangle of the control.
     * @return The bounding rectangle
     * @throws AutomationException Call to Automation API failed
     */
    public WinDef.RECT getBoundingRectangle() throws AutomationException {
        WinDef.RECT rect = new WinDef.RECT();

        if (this.element.getCurrentBoundingRectangle(rect) != 0) {
            throw new AutomationException();
        }

        return rect;
    }

    /**
     * Shows the context menu for the element
     * @throws AutomationException Failed to get the correct interface
     */
    public void showContextMenu() throws AutomationException {
        if (this.element.showContextMenu() != 0) {
            throw new AutomationException();
        }
    }
}