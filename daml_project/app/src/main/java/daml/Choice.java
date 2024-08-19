/**
 */
package daml;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Choice</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link daml.Choice#getName <em>Name</em>}</li>
 *   <li>{@link daml.Choice#getController <em>Controller</em>}</li>
 *   <li>{@link daml.Choice#getCreated_template <em>Created template</em>}</li>
 *   <li>{@link daml.Choice#getExercised_choice <em>Exercised choice</em>}</li>
 * </ul>
 *
 * @see daml.DamlPackage#getChoice()
 * @model
 * @generated
 */
public interface Choice extends EObject {
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
	 * @see daml.DamlPackage#getChoice_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link daml.Choice#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Controller</b></em>' reference list.
	 * The list contents are of type {@link daml.Party}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Controller</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Controller</em>' reference list.
	 * @see daml.DamlPackage#getChoice_Controller()
	 * @model required="true"
	 * @generated
	 */
	EList<Party> getController();

	/**
	 * Returns the value of the '<em><b>Created template</b></em>' reference list.
	 * The list contents are of type {@link daml.Template}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created template</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created template</em>' reference list.
	 * @see daml.DamlPackage#getChoice_Created_template()
	 * @model
	 * @generated
	 */
	EList<Template> getCreated_template();

	/**
	 * Returns the value of the '<em><b>Exercised choice</b></em>' reference list.
	 * The list contents are of type {@link daml.Choice}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exercised choice</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exercised choice</em>' reference list.
	 * @see daml.DamlPackage#getChoice_Exercised_choice()
	 * @model
	 * @generated
	 */
	EList<Choice> getExercised_choice();

} // Choice
