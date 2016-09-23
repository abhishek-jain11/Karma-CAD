/*
 * $Id$
 *
 * JSDAI(TM), a way to implement STEP, ISO 10303
 * Copyright (C) 1997-2008, LKSoftWare GmbH, Germany
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License
 * version 3 as published by the Free Software Foundation (AGPL v3).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * JSDAI is a registered trademark of LKSoftWare GmbH, Germany
 * This software is also available under commercial licenses.
 * See also http://www.jsdai.net/
 */

// %modified: 1016210368707 %

/* Generated By:JJTree: Do not edit this line. X_MultiplicationLikeOp.java */
package jsdai.expressCompiler;

import java.util.*;
import jsdai.lang.*;


//           "*"    operation = 1 - public void mulOrIntersect(Value val1, Value val2)
//              "/"    operation = 2 - public void divide(Value numerator, Value denominator)
//              <DIV>  operation = 3 - public void DIV(Value val1, Value val2)
//              <MOD>  operation = 4 - public void MOD(Value val1, Value val2)
//              <AND>  operation = 5 - public void AND(Value val1, Value val2) or public int AND(Value val)
//              "||"   operation = 6
public class X_MultiplicationLikeOp extends ExpressionNode {
  int op_count = 0;
	ArrayList operations; //  int[] operations;
	ArrayList operands; //  String[] operands;
  ArrayList operand_types; //  int[] operand_types;
  ArrayList exact_operand_types; // EEntity[] exact_operand_types;

  public X_MultiplicationLikeOp(int id) {
    super(id);


    operations = new ArrayList(); // operations = new int[20];
    operands = new ArrayList(); // operands = new String[20]; 
    operand_types = new ArrayList(); // operand_types = new int[20];
    exact_operand_types = new ArrayList(); // exact_operand_types = new EEntity[20];

    op_count = 0;
  }

  public X_MultiplicationLikeOp(Compiler2 p, int id) {
    super(p, id);

    operations = new ArrayList(); // operations = new int[20];
    operands = new ArrayList(); // operands = new String[20];
    operand_types = new ArrayList(); // operand_types = new int[20];
    exact_operand_types = new ArrayList(); // exact_operand_types = new EEntity[20];

    op_count = 0;
  }

  /** Accept the visitor. **/
  public Object jjtAccept(Compiler2Visitor visitor, Object data)
                   throws SdaiException {
    return visitor.visit(this, data);
  }

  /** Accept the visitor. **/
  public Object childrenAccept(Compiler2Visitor visitor, Object data)
                        throws SdaiException {
    JavaClass jc = (JavaClass) data;
    String op_type_str = "default";
    String operator_str = "DefaultMultiOperator";
    String cast_1 = "";
    String cast_2 = "";
    int operand_1_type;
    int operand_2_type;
    int result_type = 0;
    EEntity exact_result_type = null;
    String str = null;

    if (jc.flag_value) {
      if (children != null) {
        // additional java code forwarded, if present
        variable_names = new Vector();
        variable_declarations = new Vector();
        statements = new Vector();
        initializing_code = new Vector();

				op_count = 0;
        for (int i = 0; i < children.length; ++i) {
          children[i].jjtAccept(visitor, data);

          if (jc != null) {
            if (jc.active) {
              if (((SimpleNode) children[i]).java_contains_statements) {
                java_contains_statements = true;

                // variable_declaration += "\n" + ((SimpleNode)children[i]).variable_declaration;
                for (int j = 0; j < ((SimpleNode) children[i]).variable_names.size(); j++) {
                  variable_names.add(((SimpleNode) children[i]).variable_names.elementAt(j));
                }

                for (int j = 0; j < ((SimpleNode) children[i]).variable_declarations.size(); j++) {
                  variable_declarations.add(((SimpleNode) children[i]).variable_declarations.elementAt(j));
                }

                for (int j = 0; j < ((SimpleNode) children[i]).statements.size(); j++) {
                  statements.add(((SimpleNode) children[i]).statements.elementAt(j));
                }

                // initializing_code += "\n" + ((SimpleNode)children[i]).initializing_code;
                for (int j = 0; j < ((SimpleNode) children[i]).initializing_code.size(); j++) {
                  initializing_code.add(((SimpleNode) children[i]).initializing_code.elementAt(j));
                }
              } // if contains statements

              if (children[i] instanceof X_IndexQualifier) {
                str = (String)operands.get(op_count-1) + jc.generated_java;
		            operands.set(op_count-1, str);
//                operands[op_count - 1] += jc.generated_java;
              } else if (children[i] instanceof X_AttributeQualifier) {
                str = (String)operands.get(op_count-1) + jc.generated_java;
		            operands.set(op_count-1, str);
//                operands[op_count - 1] += jc.generated_java;
              } else if (children[i] instanceof X_GroupQualifier) {
                str = (String)operands.get(op_count-1) + jc.generated_java;
		            operands.set(op_count-1, str);
//                operands[op_count - 1] += jc.generated_java;
              } else {
								if (op_count >= operands.size()) {
									operands.add(jc.generated_java);
								} else {
								  // probably not needed
									operands.set(op_count, jc.generated_java);
              	}
//                operands[op_count] = jc.generated_java;
                op_count++;
              }

              printActive("XP childrenAccept  - MultiplicationLikeOp - operand nr: " + i + 
                                 ", value: " + jc.generated_java, jc);
//              operand_types[i] = jc.type_of_operand; // may be no longer needed
//              operand_types.set(i, new Integer(jc.type_of_operand));

              if (jc.type_of_operand >= JavaClass.T_AGGREGATE) { // may be no longer needed
//                exact_operand_types[i] = jc.type_of_aggregate; // may be no longer needed
//                exact_operand_types.set(i, jc.type_of_aggregate);
              }

              jc.generated_java = "";
            } // jc active
          } // jc not null
        } // for - loop through children
      } // if children

      if (jc != null) {
        if (jc.active) {
//          jc.generated_java = operands[0];
          jc.generated_java = (String)operands.get(0);


// System.out.println("XOXO op count: " + op_count);
          for (int i = 0; i < (op_count - 1); ++i) {
// System.out.println("XOXO i: " + i + ", operation: " + operations[i]);

/*
System.out.println("### operations ###########: " + operations.size());
for (int idi = 0; idi < operations.size(); idi++) {
	System.out.println("operations[" + idi + "]: " + operations.get(idi));
}
System.out.println("### operands ###########: " + operands.size());
for (int idi = 0; idi < operands.size(); idi++) {
	System.out.println("operands[" + idi + "]: " + operands.get(idi));
}
System.out.println("### operand_types ###########: " + operand_types.size());
for (int idi = 0; idi < operand_types.size(); idi++) {
	System.out.println("operand_types[" + idi + "]: " + operand_types.get(idi));
}
System.out.println("### exact_operand_types ###########: " + exact_operand_types.size());
for (int idi = 0; idi < exact_operand_types.size(); idi++) {
	System.out.println("exact_operand_types[" + idi + "]: " + exact_operand_types.get(idi));
}
System.out.println("------------------------------");
*/

//            switch (operations[i]) {
            switch (((Integer)operations.get(i)).intValue()) {
            case 1:
              operator_str = "mulOrIntersect";

              break;

            case 2:
              operator_str = "divide";

              break;

            case 3:
              operator_str = "DIV";

              break;

            case 4:
              operator_str = "MOD";

              break;

            case 5:
              operator_str = "AND";

              break;

            case 6:

              // "||"   operation = 6
              operator_str = "set"; //what_here

              break;

            default:
              printActive("XP - MultiplicationLikeOP - default operator 2, i: " + i + ", operations: " + operations + ", node: " + this, jc);
              operator_str = "mutiplicationLikeDefault";

              break;
            } // switch

            String value_instance_string;
            String alloc_type_str;

						// shit follows: i guess this id dead code since makeInstance isn't generated
            if ((jc.flag_alloc_type) && ((jc.alloc_type_depth + 1) == jc.indent) && (i == op_count - 2)) {
              alloc_type_str = jc.alloc_type;
              jc.flag_alloc_type = false;
              jc.alloc_type = "";
            } else {
							// set in X_LocalVariable, X_DeriveAttribute, X_ConstantDecl
							if(jc.alloc_type != null){
								alloc_type_str = jc.alloc_type;
							}else
								alloc_type_str = "";
            }

						boolean value_inst = false;

            if ((jc.flag_non_temporary_value_instance) &&  ((jc.assignment_depth + 1) == jc.indent) && (i == op_count - 2)) {
              value_instance_string = jc.value_instance;
              jc.flag_non_temporary_value_instance = false;
              jc.value_instance = "";
							value_inst = true;
            } else {
              value_instance_string = "Value.alloc(" + alloc_type_str + ").";
            }

// just create - start

            if (jc.generated_java.equals("_JUST_CREATE_")) { // aggregate initializer

              jc.generated_java = "Value.alloc(ExpressTypes.AGGREGATE_GENERIC_TYPE).create()";
            }

            ;

            if (((String)operands.get(i+1)).equals("_JUST_CREATE_")) { // aggregate initializer
//            if (operands[i + 1].equals("_JUST_CREATE_")) { // aggregate initializer

//              operands[i + 1] = "Value.alloc(ExpressTypes.AGGREGATE_GENERIC_TYPE).create()";
              operands.set(i+1, "Value.alloc(ExpressTypes.AGGREGATE_GENERIC_TYPE).create()");
            }

            ;

// just create - end

//            if (operations[i] == 6) { // || operator
            if (((Integer)operations.get(i)).intValue() == 6) { // || operator
							if(value_inst)
//RR-SdaiContext: assuming that other operation also have _context

//								jc.generated_java = value_instance_string + operator_str + "(_context, " + jc.generated_java + ".addComplex(" + operands[i + 1] + "))";
								jc.generated_java = value_instance_string + operator_str + "(_context, " + jc.generated_java + ".addComplex(" + (String)operands.get(i+1) + "))";
							else
//								jc.generated_java = jc.generated_java +".addComplex(" + operands[i + 1] + ")";
								jc.generated_java = jc.generated_java +".addComplex(" + (String)operands.get(i+1) + ")";


//               System.out.println("op count: " + op_count);
//               System.out.println("indent: " + jc.indent);
//               System.out.println("i :" + i);

// moved to value class:

//               if (jc.flag_non_temporary_value_instance && 
//                       ((jc.assignment_depth + 2) == (jc.indent - (op_count - 1))) && 
//                       (i == op_count - 2)) {
//                 jc.generated_java += ".makeInstance(_context)";
//               }

//               if (jc.in_derive_definition && (6 == (jc.indent - (op_count - 1))) && 
//                       (i == op_count - 2)) {
//                 jc.generated_java += ".makeInstance(_context)";
//               }

//               if (jc.in_constant_definition && (6 == (jc.indent - (op_count - 1))) && 
//                       (i == op_count - 2)) {
//                 jc.generated_java += ".makeInstance(_context)";
//               }

            } else {
							// Gintaras somehow generates type in Value							
// 							if(operations[i] == 1){ // mulOrIntersection - we cant know actual type
// 								value_instance_string = "Value.alloc(" + jc.generated_java  + ".getActualType()).";								
// 							}

//							if(operations[i] == 5){ // AND 
							if(((Integer)operations.get(i)).intValue() == 5){ // AND 
								value_instance_string = "Value.alloc(ExpressTypes.LOGICAL_TYPE).";								
//								value_instance_string = "Value.alloc().";								

//							}else if(operations[i] != 1)
							}else if(((Integer)operations.get(i)).intValue() != 1)

								value_instance_string = "Value.alloc(ExpressTypes.NUMBER_TYPE).";								
//							jc.generated_java = value_instance_string + operator_str + "(_context, " + jc.generated_java + ", " + operands[i + 1] + ")";
							jc.generated_java = value_instance_string + operator_str + "(_context, " + jc.generated_java + ", " + (String)operands.get(i+1) + ")";
						}

          } // for children
        } // if active
      } // if jc not null
    } else { // old stuff - not value

      // no old stuff here, replaced directly
    } // if not value

    if (jc != null) {
      if (jc.active) {
        jc.type_of_operand = result_type;
      }
    }

    // } // if not value
    return data;
  }
}
