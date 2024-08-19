/**
 */
package daml.impl;

import daml.DamlPackage;
import daml.Party;
import daml.SContract;
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
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>SContract</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link daml.impl.SContractImpl#getScName <em>Sc Name</em>}</li>
 *   <li>{@link daml.impl.SContractImpl#getTemplate <em>Template</em>}</li>
 *   <li>{@link daml.impl.SContractImpl#getParty <em>Party</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SContractImpl extends MinimalEObjectImpl.Container implements SContract {
	/**
	 * The default value of the '{@link #getScName() <em>Sc Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScName()
	 * @generated
	 * @ordered
	 */
	protected static final String SC_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getScName() <em>Sc Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScName()
	 * @generated
	 * @ordered
	 */
	protected String scName = SC_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTemplate() <em>Template</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTemplate()
	 * @generated
	 * @ordered
	 */
	protected EList<Template> template;

	/**
	 * The cached value of the '{@link #getParty() <em>Party</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParty()
	 * @generated
	 * @ordered
	 */
	protected EList<Party> party;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SContractImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DamlPackage.Literals.SCONTRACT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getScName() {
		return scName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScName(String newScName) {
		String oldScName = scName;
		scName = newScName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DamlPackage.SCONTRACT__SC_NAME, oldScName, scName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Template> getTemplate() {
		if (template == null) {
			template = new EObjectContainmentEList<Template>(Template.class, this, DamlPackage.SCONTRACT__TEMPLATE);
		}
		return template;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Party> getParty() {
		if (party == null) {
			party = new EObjectContainmentEList<Party>(Party.class, this, DamlPackage.SCONTRACT__PARTY);
		}
		return party;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DamlPackage.SCONTRACT__TEMPLATE:
			return ((InternalEList<?>) getTemplate()).basicRemove(otherEnd, msgs);
		case DamlPackage.SCONTRACT__PARTY:
			return ((InternalEList<?>) getParty()).basicRemove(otherEnd, msgs);
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
		case DamlPackage.SCONTRACT__SC_NAME:
			return getScName();
		case DamlPackage.SCONTRACT__TEMPLATE:
			return getTemplate();
		case DamlPackage.SCONTRACT__PARTY:
			return getParty();
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
		case DamlPackage.SCONTRACT__SC_NAME:
			setScName((String) newValue);
			return;
		case DamlPackage.SCONTRACT__TEMPLATE:
			getTemplate().clear();
			getTemplate().addAll((Collection<? extends Template>) newValue);
			return;
		case DamlPackage.SCONTRACT__PARTY:
			getParty().clear();
			getParty().addAll((Collection<? extends Party>) newValue);
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
		case DamlPackage.SCONTRACT__SC_NAME:
			setScName(SC_NAME_EDEFAULT);
			return;
		case DamlPackage.SCONTRACT__TEMPLATE:
			getTemplate().clear();
			return;
		case DamlPackage.SCONTRACT__PARTY:
			getParty().clear();
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
		case DamlPackage.SCONTRACT__SC_NAME:
			return SC_NAME_EDEFAULT == null ? scName != null : !SC_NAME_EDEFAULT.equals(scName);
		case DamlPackage.SCONTRACT__TEMPLATE:
			return template != null && !template.isEmpty();
		case DamlPackage.SCONTRACT__PARTY:
			return party != null && !party.isEmpty();
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
		result.append(" (scName: ");
		result.append(scName);
		result.append(')');
		return result.toString();
	}

} //SContractImpl
