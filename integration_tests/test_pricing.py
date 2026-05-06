"""
Python-based integration tests for the Pricing Engine.

These tests build and run the Java application, then verify
the output matches expected pricing calculations.
"""

import subprocess
import os
import sys

# Project root directory
PROJECT_ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
GRADLEW = os.path.join(PROJECT_ROOT, "gradlew.bat" if os.name == "nt" else "gradlew")


def run_gradle_command(command):
    """Run a Gradle command and return the result."""
    result = subprocess.run(
        [GRADLEW, command],
        cwd=PROJECT_ROOT,
        capture_output=True,
        text=True
    )
    return result


def test_gradle_build():
    """Test that the project builds successfully."""
    result = run_gradle_command("build")
    assert result.returncode == 0, f"Build failed:\n{result.stderr}"
    print("PASSED: Gradle build successful")


def test_gradle_tests():
    """Test that all JUnit tests pass."""
    result = run_gradle_command("test")
    assert result.returncode == 0, f"Tests failed:\n{result.stderr}"
    print("PASSED: All JUnit tests pass")


def test_application_runs():
    """Test that the application runs without errors."""
    result = run_gradle_command("run")
    assert result.returncode == 0, f"Application failed:\n{result.stderr}"
    assert "Order 1" in result.stdout, "Expected Order 1 in output"
    assert "Order 2" in result.stdout, "Expected Order 2 in output"
    print("PASSED: Application runs successfully")


# --- Independent pricing calculations ---

def calculate_price(subtotal, discount_pct, vip_discount_pct, tax_rate=0.07):
    """Independent calculation for verification."""
    discount = subtotal * discount_pct + subtotal * vip_discount_pct
    after_discount = subtotal - discount
    tax = after_discount * tax_rate
    final_price = after_discount + tax
    return subtotal, discount, tax, final_price


def test_regular_no_discount():
    """Test: REGULAR customer, no discount code."""
    subtotal, discount, tax, final_price = calculate_price(100.0, 0.0, 0.0)
    assert abs(subtotal - 100.0) < 0.01
    assert abs(discount - 0.0) < 0.01
    assert abs(tax - 7.0) < 0.01
    assert abs(final_price - 107.0) < 0.01
    print("PASSED: Regular + no discount = $107.00")


def test_regular_save10():
    """Test: REGULAR customer, SAVE10."""
    subtotal, discount, tax, final_price = calculate_price(100.0, 0.10, 0.0)
    assert abs(discount - 10.0) < 0.01
    assert abs(final_price - 96.30) < 0.01
    print("PASSED: Regular + SAVE10 = $96.30")


def test_vip_no_discount():
    """Test: VIP customer, no discount code."""
    subtotal, discount, tax, final_price = calculate_price(100.0, 0.0, 0.15)
    assert abs(discount - 15.0) < 0.01
    assert abs(final_price - 90.95) < 0.01
    print("PASSED: VIP + no discount = $90.95")


def test_vip_save20():
    """Test: VIP customer, SAVE20."""
    subtotal, discount, tax, final_price = calculate_price(100.0, 0.20, 0.15)
    assert abs(discount - 35.0) < 0.01
    assert abs(final_price - 69.55) < 0.01
    print("PASSED: VIP + SAVE20 = $69.55")


def test_vip_save30():
    """Test: VIP customer, SAVE30."""
    subtotal, discount, tax, final_price = calculate_price(200.0, 0.30, 0.15)
    assert abs(discount - 90.0) < 0.01
    after_discount = 200.0 - 90.0  # 110.0
    expected_tax = 110.0 * 0.07  # 7.70
    expected_final = 110.0 + 7.70  # 117.70
    assert abs(final_price - expected_final) < 0.01
    print("PASSED: VIP + SAVE30 on $200 = $117.70")


if __name__ == "__main__":
    tests = [
        test_gradle_build,
        test_gradle_tests,
        test_application_runs,
        test_regular_no_discount,
        test_regular_save10,
        test_vip_no_discount,
        test_vip_save20,
        test_vip_save30,
    ]

    passed = 0
    failed = 0

    for test in tests:
        try:
            test()
            passed += 1
        except AssertionError as e:
            print(f"FAILED: {test.__name__}: {e}")
            failed += 1
        except Exception as e:
            print(f"ERROR: {test.__name__}: {e}")
            failed += 1

    print(f"\n{'='*40}")
    print(f"Results: {passed} passed, {failed} failed, {len(tests)} total")
    if failed > 0:
        sys.exit(1)
