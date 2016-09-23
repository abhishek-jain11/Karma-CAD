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

/* Generated By:JJTree: Do not edit this line. X_EntityConstructorParameter.java */

package jsdai.expressCompiler;

import java.util.*;
import jsdai.SExtended_dictionary_schema.*;
import jsdai.lang.*;

public class X_EntityConstructorParameter extends SimpleNode {
  public X_EntityConstructorParameter(int id) {
    super(id);
  }

  public X_EntityConstructorParameter(Compiler2 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(Compiler2Visitor visitor, Object data) throws jsdai.lang.SdaiException {
    return visitor.visit(this, data);


  }

  public Object childrenAccept(Compiler2Visitor visitor, Object data)
                        throws SdaiException {
    JavaClass jc = (JavaClass) data;

    if (children != null) {
      // for contained statements
      variable_names = new Vector();
      variable_declarations = new Vector();
      statements = new Vector();
      initializing_code = new Vector();

      for (int i = 0; i < children.length; ++i) {
        children[i].jjtAccept(visitor, data);

        if (jc != null) {
          if (jc.active) {
            if (((SimpleNode) children[i]).java_contains_statements) {
              java_contains_statements = true;

              for (int j = 0; j < ((SimpleNode) children[i]).variable_names.size(); j++) {
                variable_names.add(((SimpleNode) children[i]).variable_names.elementAt(j));
              }

              for (int j = 0; j < ((SimpleNode) children[i]).variable_declarations.size(); j++) {
                variable_declarations.add(((SimpleNode) children[i]).variable_declarations.elementAt(j));
              }

              for (int j = 0; j < ((SimpleNode) children[i]).statements.size(); j++) {
                statements.add(((SimpleNode) children[i]).statements.elementAt(j));
              }

              for (int j = 0; j < ((SimpleNode) children[i]).initializing_code.size(); j++) {
                initializing_code.add(((SimpleNode) children[i]).initializing_code.elementAt(j));
              }
            }

					}
        }
      }

			//			jc.print_string
	    if (jc.generated_java.equals("_JUST_CREATE_")) {
             jc.generated_java = "Value.alloc(ExpressTypes.AGGREGATE_GENERIC_TYPE).create()"; 
			}		
			jc.generated_java = (".addParameter(" + jc.generated_java + ")");
    }
		return data;
	}

}
