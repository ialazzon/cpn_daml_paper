/**
 */
package daml;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link daml.Template#getName <em>Name</em>}</li>
 *   <li>{@link daml.Template#getObserver <em>Observer</em>}</li>
 *   <li>{@link daml.Template#getSignatory <em>Signatory</em>}</li>
 *   <li>{@link daml.Template#getChoice <em>Choice</em>}</li>
 *   <li>{@link daml.Template#getReferenced_template <em>Referenced template</em>}</li>
 * </ul>
 *
 * @see daml.DamlPackage#getTemplate()
 * @model
 * @generated
 */
public interface Template extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see daml.DamlPackage#getTemplate_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link daml.Template#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Observer</b></em>' reference list.
	 * The list contents are of type {@link daml.Party}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Observer</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Observer</em>' reference list.
	 * @see daml.DamlPackage#getTemplate_Observer()
	 * @model
	 * @generated
	 */
	EList<Party> getObserver();

	/**
	 * Returns the value of the '<em><b>Signatory</b></em>' reference list.
	 * The list contents are of type {@link daml.Party}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Signatory</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Signatory</em>' reference list.
	 * @see daml.DamlPackage#getTemplate_Signatory()
	 * @model required="true"
	 * @generated
	 */
	EList<Party> getSignatory();

	/**
	 * Returns the value of the '<em><b>Choice</b></em>' containment reference list.
	 * The list contents are of type {@link daml.Choice}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Choice</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Choice</em>' containment reference list.
	 * @see daml.DamlPackage#getTemplate_Choice()
	 * @model containment="true"
	 * @generated
	 */
	EList<Choice> getChoice();

	/**
	 * Returns the value of the '<em><b>Referenced template</b></em>' reference list.
	 * The list contents are of type {@link daml.Template}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced template</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced template</em>' reference list.
	 * @see daml.DamlPackage#getTemplate_Referenced_template()
	 * @model
	 * @generated
	 */
	EList<Template> getReferenced_template();

} // Template
