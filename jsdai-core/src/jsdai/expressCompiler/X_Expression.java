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

// %modified: 1016210367530 %

/* Generated By:JJTree: Do not edit this line. X_Expression.java */
package jsdai.expressCompiler;

public class X_Expression
  extends ExpressionNode {

  // public class X_Expression extends SimpleNode {
  public X_Expression(int id) {
    super(id);
  }

  public X_Expression(Compiler2 p, int id) {
    super(p, id);
  }

  /** Accept the visitor. **/
  public Object jjtAccept(Compiler2Visitor visitor, Object data)
                   throws jsdai.lang.SdaiException {
    return visitor.visit(this, data);
  }
}