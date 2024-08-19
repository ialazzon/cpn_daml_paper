/**
 */
package daml.impl;

import daml.Choice;
import daml.DamlPackage;
import daml.Party;
import daml.Template;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link daml.impl.TemplateImpl#getName <em>Name</em>}</li>
 *   <li>{@link daml.impl.TemplateImpl#getObserver <em>Observer</em>}</li>
 *   <li>{@link daml.impl.TemplateImpl#getSignatory <em>Signatory</em>}</li>
 *   <li>{@link daml.impl.TemplateImpl#getChoice <em>Choice</em>}</li>
 *   <li>{@link daml.impl.TemplateImpl#getReferenced_template <em>Referenced template</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TemplateImpl extends MinimalEObjectImpl.Container implements Template {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getObserver() <em>Observer</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObserver()
	 * @generated
	 * @ordered
	 */
	protected EList<Party> observer;

	/**
	 * The cached value of the '{@link #getSignatory() <em>Signatory</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignatory()
	 * @generated
	 * @ordered
	 */
	protected EList<Party> signatory;

	/**
	 * The cached value of the '{@link #getChoice() <em>Choice</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChoice()
	 * @generated
	 * @ordered
	 */
	protected EList<Choice> choice;

	/**
	 * The cached value of the '{@link #getReferenced_template() <em>Referenced template</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenced_template()
	 * @generated
	 * @ordered
	 */
	protected EList<Template> referenced_template;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DamlPackage.Literals.TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DamlPackage.TEMPLATE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Party> getObserver() {
		if (observer == null) {
			observer = new EObjectResolvingEList<Party>(Party.class, this, DamlPackage.TEMPLATE__OBSERVER);
		}
		return observer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Party> getSignatory() {
		if (signatory == null) {
			signatory = new EObjectResolvingEList<Party>(Party.class, this, DamlPackage.TEMPLATE__SIGNATORY);
		}
		return signatory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Choice> getChoice() {
		if (choice == null) {
			choice = new EObjectContainmentEList<Choice>(Choice.class, this, DamlPackage.TEMPLATE__CHOICE);
		}
		return choice;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Template> getReferenced_template() {
		if (referenced_template == null) {
			referenced_template = new EObjectResolvingEList<Template>(Template.class, this,
					DamlPackage.TEMPLATE__REFERENCED_TEMPLATE);
		}
		return referenced_template;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DamlPackage.TEMPLATE__CHOICE:
			return ((InternalEList<?>) getChoice()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DamlPackage.TEMPLATE__NAME:
			return getName();
		case DamlPackage.TEMPLATE__OBSERVER:
			return getObserver();
		case DamlPackage.TEMPLATE__SIGNATORY:
			return getSignatory();
		case DamlPackage.TEMPLATE__CHOICE:
			return getChoice();
		case DamlPackage.TEMPLATE__REFERENCED_TEMPLATE:
			return getReferenced_template();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case DamlPackage.TEMPLATE__NAME:
			setName((String) newValue);
			return;
		case DamlPackage.TEMPLATE__OBSERVER:
			getObserver().clear();
			getObserver().addAll((Collection<? extends Party>) newValue);
			return;
		case DamlPackage.TEMPLATE__SIGNATORY:
			getSignatory().clear();
			getSignatory().addAll((Collection<? extends Party>) newValue);
			return;
		case DamlPackage.TEMPLATE__CHOICE:
			getChoice().clear();
			getChoice().addAll((Collection<? extends Choice>) newValue);
			return;
		case DamlPackage.TEMPLATE__REFERENCED_TEMPLATE:
			getReferenced_template().clear();
			getReferenced_template().addAll((Collection<? extends Template>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case DamlPackage.TEMPLATE__NAME:
			setName(NAME_EDEFAULT);
			return;
		case DamlPackage.TEMPLATE__OBSERVER:
			getObserver().clear();
			return;
		case DamlPackage.TEMPLATE__SIGNATORY:
			getSignatory().clear();
			return;
		case DamlPackage.TEMPLATE__CHOICE:
			getChoice().clear();
			return;
		case DamlPackage.TEMPLATE__REFERENCED_TEMPLATE:
			getReferenced_template().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case DamlPackage.TEMPLATE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case DamlPackage.TEMPLATE__OBSERVER:
			return observer != null && !observer.isEmpty();
		case DamlPackage.TEMPLATE__SIGNATORY:
			return signatory != null && !signatory.isEmpty();
		case DamlPackage.TEMPLATE__CHOICE:
			return choice != null && !choice.isEmpty();
		case DamlPackage.TEMPLATE__REFERENCED_TEMPLATE:
			return referenced_template != null && !referenced_template.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //TemplateImpl
