/**
 */
package daml;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>SContract</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link daml.SContract#getScName <em>Sc Name</em>}</li>
 *   <li>{@link daml.SContract#getTemplate <em>Template</em>}</li>
 *   <li>{@link daml.SContract#getParty <em>Party</em>}</li>
 * </ul>
 *
 * @see daml.DamlPackage#getSContract()
 * @model
 * @generated
 */
public interface SContract extends EObject {
	/**
	 * Returns the value of the '<em><b>Sc Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sc Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sc Name</em>' attribute.
	 * @see #setScName(String)
	 * @see daml.DamlPackage#getSContract_ScName()
	 * @model
	 * @generated
	 */
	String getScName();

	/**
	 * Sets the value of the '{@link daml.SContract#getScName <em>Sc Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sc Name</em>' attribute.
	 * @see #getScName()
	 * @generated
	 */
	void setScName(String value);

	/**
	 * Returns the value of the '<em><b>Template</b></em>' containment reference list.
	 * The list contents are of type {@link daml.Template}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Template</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Template</em>' containment reference list.
	 * @see daml.DamlPackage#getSContract_Template()
	 * @model containment="true"
	 * @generated
	 */
	EList<Template> getTemplate();

	/**
	 * Returns the value of the '<em><b>Party</b></em>' containment reference list.
	 * The list contents are of type {@link daml.Party}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Party</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Party</em>' containment reference list.
	 * @see daml.DamlPackage#getSContract_Party()
	 * @model containment="true"
	 * @generated
	 */
	EList<Party> getParty();

} // SContract
