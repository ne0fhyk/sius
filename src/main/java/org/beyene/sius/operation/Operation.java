/*
 * Copyright 2014 Mikael Beyene
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.beyene.sius.operation;

import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.dimension.composition.util.Fraction;
import org.beyene.sius.operation.functor.Adder;
import org.beyene.sius.operation.functor.FunctorFactory;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitId;
import org.beyene.sius.unit.composition.FractionUnit;

/**
 * 
 * @author mbeyene
 * 
 */
public final class Operation {

	private Operation() {
		// private constructor to prevent instantiation
	}

	/**
	 * Converts a unit into another unit of same dimension.
	 * 
	 * @param op
	 *            unit to convert
	 * @param cunitId
	 *            identifier of new unit
	 * @return value of <code>op</code> converted to new unit.
	 */
	public static <D extends Dimension<D>, B extends Unit<D, B, B>, OP extends Unit<D, B, OP>, CU extends Unit<D, B, CU>, CuId extends UnitId<D, B, CU>> CU convert(
			OP op, CuId cunitId) {
		return Converter.convert(op, cunitId);
	}

	/**
	 * Adds (different) units of the same dimension. Result is of same type as
	 * first operand.
	 * 
	 * @param op1
	 *            first operand. Determines type of result.
	 * @param op2
	 *            second operand. Gets added to first operand.
	 * @return sum
	 */
	public static <D extends Dimension<D>, B extends Unit<D, B, B>, OP1 extends Unit<D, B, OP1>, OP2 extends Unit<D, B, OP2>> OP1 add(
			OP1 op1, OP2 op2) {
		return op1.valueOf(op1.getScalar() + op1.convert(op2).getScalar());
	}

	/**
	 * Adds (different) units of the same dimension. Result is of same type as
	 * first operand.
	 * 
	 * @param op1
	 *            first operand. Determines type of result.
	 * @param op2
	 *            second operand. Gets added to first operand.
	 * @return sum
	 */
	public static <D extends Dimension<D>, B extends Unit<D, B, B>, OP1 extends Unit<D, B, OP1>, OP2 extends Unit<D, B, OP2>, OP3 extends Unit<D, B, OP3>> OP1 add(
			OP1 op1, OP2 op2, OP3 op3) {
		return op1.valueOf(op1.getScalar() + op1.convert(op2).getScalar()
				+ op1.convert(op3).getScalar());
	}

	/**
	 * Adds (different) units of the same dimension.
	 * 
	 * @param cunitId
	 *            resulting unit
	 * @param op
	 *            first operand
	 * @param ops
	 *            further operands
	 * @return sum
	 */
	@SafeVarargs
	public static <D extends Dimension<D>, B extends Unit<D, B, B>, OP extends Unit<D, B, OP>, CU extends Unit<D, B, CU>, CUID extends UnitId<D, B, CU>> CU add(
			CUID cunitId, OP op, Unit<D, B, ?>... ops) {
		Adder<D, B, CU> adder = FunctorFactory.sum(cunitId).op(op);
		for (Unit<D, B, ?> o : ops)
			adder.op(o);
		return adder.apply();
	}

	/**
	 * Subtracts two (different) units of the same dimension. Result is of same
	 * type as first operand.
	 * 
	 * @param op1
	 *            first operand. Determines type of result.
	 * @param op2
	 *            second operand. Gets subtracted from first operand.
	 * @return difference
	 */
	public static <D extends Dimension<D>, B extends Unit<D, B, B>, OP1 extends Unit<D, B, OP1>, OP2 extends Unit<D, B, OP2>> OP1 sub(
			OP1 op1, OP2 op2) {
		return op1.valueOf(op1.getScalar() - op1.convert(op2).getScalar());
	}

	/**
	 * Scalar multiplication.
	 * 
	 * @param op
	 *            operand and first factor
	 * @param scalar
	 *            scalar and second factor
	 * @return product
	 */
	public static <D extends Dimension<D>, B extends Unit<D, B, B>, OP extends Unit<D, B, OP>> OP mul(
			OP op, double scalar) {
		return op.valueOf(op.getScalar() * scalar);
	}

	/**
	 * Scalar division.
	 * 
	 * @param op
	 *            dividend
	 * @param scalar
	 *            divisor
	 * @return quotient
	 */
	public static <D extends Dimension<D>, B extends Unit<D, B, B>, OP extends Unit<D, B, OP>> OP div(
			OP op, double scalar) {
		return op.valueOf(op.getScalar() / scalar);
	}

	/**
	 * Multiplication of fraction unit and unit.
	 * 
	 * Multiplies a fraction unit of type a/b with a unit of type b.
	 * 
	 * @param factor1
	 *            unit of fraction type
	 * @param factor2
	 *            unit of fraction's denominator dimension
	 * @return unit of fraction's numerator type
	 */
	public static <NUMERATOR extends Dimension<NUMERATOR>,
	DENOMINATOR extends Dimension<DENOMINATOR>,
	F extends Fraction<NUMERATOR, DENOMINATOR, F>,
	BASE_F extends Unit<F, BASE_F, BASE_F>,
	BASE_NUMERATOR extends Unit<NUMERATOR, BASE_NUMERATOR, BASE_NUMERATOR>,
	BASE_DENOMINATOR extends Unit<DENOMINATOR, BASE_DENOMINATOR, BASE_DENOMINATOR>,
	UNIT_NUMERATOR extends Unit<NUMERATOR, BASE_NUMERATOR, UNIT_NUMERATOR>,
	UNIT_DENOMINATOR extends Unit<DENOMINATOR, BASE_DENOMINATOR, UNIT_DENOMINATOR>,
	UNIT_FRACTION extends FractionUnit<NUMERATOR, DENOMINATOR, F, BASE_F, BASE_NUMERATOR, BASE_DENOMINATOR, UNIT_NUMERATOR, UNIT_DENOMINATOR, UNIT_FRACTION>,
	OP extends Unit<DENOMINATOR, BASE_DENOMINATOR, OP>> UNIT_NUMERATOR mul(
			FractionUnit<NUMERATOR, DENOMINATOR, F, BASE_F, BASE_NUMERATOR, BASE_DENOMINATOR, UNIT_NUMERATOR, UNIT_DENOMINATOR, UNIT_FRACTION> factor1, OP factor2) {
		double scalarF2 = factor1.getComponentUnit2().convert(factor2).getScalar();
		return factor1.getComponentUnit1().valueOf(factor1.getScalar() * scalarF2);
	}
}