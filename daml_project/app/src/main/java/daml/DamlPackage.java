/**
 */
package daml;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see daml.DamlFactory
 * @model kind="package"
 * @generated
 */
public interface DamlPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "daml";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/daml";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "daml";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DamlPackage eINSTANCE = daml.impl.DamlPackageImpl.init();

	/**
	 * The meta object id for the '{@link daml.impl.SContractImpl <em>SContract</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see daml.impl.SContractImpl
	 * @see daml.impl.DamlPackageImpl#getSContract()
	 * @generated
	 */
	int SCONTRACT = 0;

	/**
	 * The feature id for the '<em><b>Sc Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCONTRACT__SC_NAME = 0;

	/**
	 * The feature id for the '<em><b>Template</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCONTRACT__TEMPLATE = 1;

	/**
	 * The feature id for the '<em><b>Party</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCONTRACT__PARTY = 2;

	/**
	 * The number of structural features of the '<em>SContract</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCONTRACT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>SContract</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCONTRACT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link daml.impl.TemplateImpl <em>Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see daml.impl.TemplateImpl
	 * @see daml.impl.DamlPackageImpl#getTemplate()
	 * @generated
	 */
	int TEMPLATE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Observer</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__OBSERVER = 1;

	/**
	 * The feature id for the '<em><b>Signatory</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__SIGNATORY = 2;

	/**
	 * The feature id for the '<em><b>Choice</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__CHOICE = 3;

	/**
	 * The feature id for the '<em><b>Referenced template</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__REFERENCED_TEMPLATE = 4;

	/**
	 * The number of structural features of the '<em>Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link daml.impl.PartyImpl <em>Party</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see daml.impl.PartyImpl
	 * @see daml.impl.DamlPackageImpl#getParty()
	 * @generated
	 */
	int PARTY = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTY__NAME = 0;

	/**
	 * The number of structural features of the '<em>Party</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Party</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link daml.impl.ChoiceImpl <em>Choice</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see daml.impl.ChoiceImpl
	 * @see daml.impl.DamlPackageImpl#getChoice()
	 * @generated
	 */
	int CHOICE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Controller</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__CONTROLLER = 1;

	/**
	 * The feature id for the '<em><b>Created template</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__CREATED_TEMPLATE = 2;

	/**
	 * The feature id for the '<em><b>Exercised choice</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE__EXERCISED_CHOICE = 3;

	/**
	 * The number of structural features of the '<em>Choice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Choice</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHOICE_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link daml.SContract <em>SContract</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>SContract</em>'.
	 * @see daml.SContract
	 * @generated
	 */
	EClass getSContract();

	/**
	 * Returns the meta object for the attribute '{@link daml.SContract#getScName <em>Sc Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sc Name</em>'.
	 * @see daml.SContract#getScName()
	 * @see #getSContract()
	 * @generated
	 */
	EAttribute getSContract_ScName();

	/**
	 * Returns the meta object for the containment reference list '{@link daml.SContract#getTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Template</em>'.
	 * @see daml.SContract#getTemplate()
	 * @see #getSContract()
	 * @generated
	 */
	EReference getSContract_Template();

	/**
	 * Returns the meta object for the containment reference list '{@link daml.SContract#getParty <em>Party</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Party</em>'.
	 * @see daml.SContract#getParty()
	 * @see #getSContract()
	 * @generated
	 */
	EReference getSContract_Party();

	/**
	 * Returns the meta object for class '{@link daml.Template <em>Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Template</em>'.
	 * @see daml.Template
	 * @generated
	 */
	EClass getTemplate();

	/**
	 * Returns the meta object for the attribute '{@link daml.Template#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see daml.Template#getName()
	 * @see #getTemplate()
	 * @generated
	 */
	EAttribute getTemplate_Name();

	/**
	 * Returns the meta object for the reference list '{@link daml.Template#getObserver <em>Observer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Observer</em>'.
	 * @see daml.Template#getObserver()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Observer();

	/**
	 * Returns the meta object for the reference list '{@link daml.Template#getSignatory <em>Signatory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Signatory</em>'.
	 * @see daml.Template#getSignatory()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Signatory();

	/**
	 * Returns the meta object for the containment reference list '{@link daml.Template#getChoice <em>Choice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Choice</em>'.
	 * @see daml.Template#getChoice()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Choice();

	/**
	 * Returns the meta object for the reference list '{@link daml.Template#getReferenced_template <em>Referenced template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Referenced template</em>'.
	 * @see daml.Template#getReferenced_template()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Referenced_template();

	/**
	 * Returns the meta object for class '{@link daml.Party <em>Party</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Party</em>'.
	 * @see daml.Party
	 * @generated
	 */
	EClass getParty();

	/**
	 * Returns the meta object for the attribute '{@link daml.Party#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see daml.Party#getName()
	 * @see #getParty()
	 * @generated
	 */
	EAttribute getParty_Name();

	/**
	 * Returns the meta object for class '{@link daml.Choice <em>Choice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Choice</em>'.
	 * @see daml.Choice
	 * @generated
	 */
	EClass getChoice();

	/**
	 * Returns the meta object for the attribute '{@link daml.Choice#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see daml.Choice#getName()
	 * @see #getChoice()
	 * @generated
	 */
	EAttribute getChoice_Name();

	/**
	 * Returns the meta object for the reference list '{@link daml.Choice#getController <em>Controller</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Controller</em>'.
	 * @see daml.Choice#getController()
	 * @see #getChoice()
	 * @generated
	 */
	EReference getChoice_Controller();

	/**
	 * Returns the meta object for the reference list '{@link daml.Choice#getCreated_template <em>Created template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Created template</em>'.
	 * @see daml.Choice#getCreated_template()
	 * @see #getChoice()
	 * @generated
	 */
	EReference getChoice_Created_template();

	/**
	 * Returns the meta object for the reference list '{@link daml.Choice#getExercised_choice <em>Exercised choice</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Exercised choice</em>'.
	 * @see daml.Choice#getExercised_choice()
	 * @see #getChoice()
	 * @generated
	 */
	EReference getChoice_Exercised_choice();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DamlFactory getDamlFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link daml.impl.SContractImpl <em>SContract</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see daml.impl.SContractImpl
		 * @see daml.impl.DamlPackageImpl#getSContract()
		 * @generated
		 */
		EClass SCONTRACT = eINSTANCE.getSContract();

		/**
		 * The meta object literal for the '<em><b>Sc Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCONTRACT__SC_NAME = eINSTANCE.getSContract_ScName();

		/**
		 * The meta object literal for the '<em><b>Template</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCONTRACT__TEMPLATE = eINSTANCE.getSContract_Template();

		/**
		 * The meta object literal for the '<em><b>Party</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCONTRACT__PARTY = eINSTANCE.getSContract_Party();

		/**
		 * The meta object literal for the '{@link daml.impl.TemplateImpl <em>Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see daml.impl.TemplateImpl
		 * @see daml.impl.DamlPackageImpl#getTemplate()
		 * @generated
		 */
		EClass TEMPLATE = eINSTANCE.getTemplate();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEMPLATE__NAME = eINSTANCE.getTemplate_Name();

		/**
		 * The meta object literal for the '<em><b>Observer</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE__OBSERVER = eINSTANCE.getTemplate_Observer();

		/**
		 * The meta object literal for the '<em><b>Signatory</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE__SIGNATORY = eINSTANCE.getTemplate_Signatory();

		/**
		 * The meta object literal for the '<em><b>Choice</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE__CHOICE = eINSTANCE.getTemplate_Choice();

		/**
		 * The meta object literal for the '<em><b>Referenced template</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEMPLATE__REFERENCED_TEMPLATE = eINSTANCE.getTemplate_Referenced_template();

		/**
		 * The meta object literal for the '{@link daml.impl.PartyImpl <em>Party</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see daml.impl.PartyImpl
		 * @see daml.impl.DamlPackageImpl#getParty()
		 * @generated
		 */
		EClass PARTY = eINSTANCE.getParty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARTY__NAME = eINSTANCE.getParty_Name();

		/**
		 * The meta object literal for the '{@link daml.impl.ChoiceImpl <em>Choice</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see daml.impl.ChoiceImpl
		 * @see daml.impl.DamlPackageImpl#getChoice()
		 * @generated
		 */
		EClass CHOICE = eINSTANCE.getChoice();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHOICE__NAME = eINSTANCE.getChoice_Name();

		/**
		 * The meta object literal for the '<em><b>Controller</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE__CONTROLLER = eINSTANCE.getChoice_Controller();

		/**
		 * The meta object literal for the '<em><b>Created template</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE__CREATED_TEMPLATE = eINSTANCE.getChoice_Created_template();

		/**
		 * The meta object literal for the '<em><b>Exercised choice</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHOICE__EXERCISED_CHOICE = eINSTANCE.getChoice_Exercised_choice();

	}

} //DamlPackage
