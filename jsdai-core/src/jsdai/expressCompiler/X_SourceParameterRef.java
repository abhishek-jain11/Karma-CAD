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

/* Generated By:JJTree: Do not edit this line. X_SourceParameterRef.java */

package jsdai.expressCompiler;

public class X_SourceParameterRef extends SimpleNode {
  String name;
	int order;
	jsdai.SExtended_dictionary_schema.ESource_parameter source_parameter;
	jsdai.SExtended_dictionary_schema.EMap_or_view_partition partition;

  public X_SourceParameterRef(int id) {
    super(id);
  }

  public X_SourceParameterRef(Compiler2 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(Compiler2Visitor visitor, Object data) throws jsdai.lang.SdaiException {
    return visitor.visit(this, data);
  }

  public void dump(String prefix) {
    
		String partition_name = "DEFAULT";
    try {
    	if (partition.testName(null)) {
    		partition_name = partition.getName(null);
    	} else {
    		partition_name = "DEFAULT";
    	}
    } catch (jsdai.lang.SdaiException sx) {
      System.out.println("SdaiException while reading partition name: " + partition);
    } catch (java.lang.NullPointerException sy) {
      System.out.println("Null pointer exception while reading partition name: " + partition);
    } 

    System.out.println(toString(prefix) + " - " + name + ", partition: " + partition_name);

    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        SimpleNode n = (SimpleNode) children[i];

        if (n != null) {
          n.dump(prefix + " ");
        }
      }
    }
  }


}

