/**
 */
package daml.impl;

import daml.Choice;
import daml.DamlPackage;
import daml.Party;

import daml.Template;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Choice</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link daml.impl.ChoiceImpl#getName <em>Name</em>}</li>
 *   <li>{@link daml.impl.ChoiceImpl#getController <em>Controller</em>}</li>
 *   <li>{@link daml.impl.ChoiceImpl#getCreated_template <em>Created template</em>}</li>
 *   <li>{@link daml.impl.ChoiceImpl#getExercised_choice <em>Exercised choice</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ChoiceImpl extends MinimalEObjectImpl.Container implements Choice {
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
	 * The cached value of the '{@link #getController() <em>Controller</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getController()
	 * @generated
	 * @ordered
	 */
	protected EList<Party> controller;

	/**
	 * The cached value of the '{@link #getCreated_template() <em>Created template</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreated_template()
	 * @generated
	 * @ordered
	 */
	protected EList<Template> created_template;

	/**
	 * The cached value of the '{@link #getExercised_choice() <em>Exercised choice</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExercised_choice()
	 * @generated
	 * @ordered
	 */
	protected EList<Choice> exercised_choice;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChoiceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DamlPackage.Literals.CHOICE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, DamlPackage.CHOICE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Party> getController() {
		if (controller == null) {
			controller = new EObjectResolvingEList<Party>(Party.class, this, DamlPackage.CHOICE__CONTROLLER);
		}
		return controller;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Template> getCreated_template() {
		if (created_template == null) {
			created_template = new EObjectResolvingEList<Template>(Template.class, this,
					DamlPackage.CHOICE__CREATED_TEMPLATE);
		}
		return created_template;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Choice> getExercised_choice() {
		if (exercised_choice == null) {
			exercised_choice = new EObjectResolvingEList<Choice>(Choice.class, this,
					DamlPackage.CHOICE__EXERCISED_CHOICE);
		}
		return exercised_choice;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case DamlPackage.CHOICE__NAME:
			return getName();
		case DamlPackage.CHOICE__CONTROLLER:
			return getController();
		case DamlPackage.CHOICE__CREATED_TEMPLATE:
			return getCreated_template();
		case DamlPackage.CHOICE__EXERCISED_CHOICE:
			return getExercised_choice();
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
		case DamlPackage.CHOICE__NAME:
			setName((String) newValue);
			return;
		case DamlPackage.CHOICE__CONTROLLER:
			getController().clear();
			getController().addAll((Collection<? extends Party>) newValue);
			return;
		case DamlPackage.CHOICE__CREATED_TEMPLATE:
			getCreated_template().clear();
			getCreated_template().addAll((Collection<? extends Template>) newValue);
			return;
		case DamlPackage.CHOICE__EXERCISED_CHOICE:
			getExercised_choice().clear();
			getExercised_choice().addAll((Collection<? extends Choice>) newValue);
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
		case DamlPackage.CHOICE__NAME:
			setName(NAME_EDEFAULT);
			return;
		case DamlPackage.CHOICE__CONTROLLER:
			getController().clear();
			return;
		case DamlPackage.CHOICE__CREATED_TEMPLATE:
			getCreated_template().clear();
			return;
		case DamlPackage.CHOICE__EXERCISED_CHOICE:
			getExercised_choice().clear();
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
		case DamlPackage.CHOICE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case DamlPackage.CHOICE__CONTROLLER:
			return controller != null && !controller.isEmpty();
		case DamlPackage.CHOICE__CREATED_TEMPLATE:
			return created_template != null && !created_template.isEmpty();
		case DamlPackage.CHOICE__EXERCISED_CHOICE:
			return exercised_choice != null && !exercised_choice.isEmpty();
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

} //ChoiceImpl
