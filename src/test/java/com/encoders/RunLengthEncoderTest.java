package com.encoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RunLengthEncoderTest {

    private RunLengthEncoding sut;

    @BeforeEach
    void setUp() {
        sut = new RunLengthEncoding();
    }

    @ParameterizedTest
    @MethodSource("provideInputForEncoding")
    void testEncode(String input, String expected) {
        String actual = sut.encode(input);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidInput")
    void testEncode_whenProvideInvalidInput(String input) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> sut.encode(input),
                "source should not be encoded: null or empty");
       assertThat(exception.getMessage()).isEqualTo("source can't be encoded: null or empty");
    }

    @ParameterizedTest
    @MethodSource("provideInputForDecoding")
    void testDecode(String input, String expected) {
        String actual = sut.decode(input);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidInputForDecode")
    void testDecode_whenProvideInvalidInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> sut.decode(input),
                "source can't be decoded");
    }

    private static Stream<Arguments> provideInputForEncoding() {
        return Stream.of(
                Arguments.of("xxxyyziiii", "X3Y2Z1I4"),
                Arguments.of("aaabbccccd", "A3B2C4D1"),
                Arguments.of("KKKKKKIIIIIIUUUUASDFGLLLL", "K6I6U4A1S1D1F1G1L4")
        );
    }

    private static Stream<Arguments> provideInputForDecoding() {
        return Stream.of(
                Arguments.of("x3y2z1i4", "XXXYYZIIII"),
                Arguments.of("A3B2C4D1", "AAABBCCCCD"),
                Arguments.of("R4a1m7k3h1d2", "RRRRAMMMMMMMKKKHDD"),
                Arguments.of("K6I6U4A1S1D1F1G1L4", "KKKKKKIIIIIIUUUUASDFGLLLL")
        );
    }

    private static Stream<String> provideInvalidInput() {
        return Stream.of("", "      ", null);
    }

    private static Stream<String> provideInvalidInputForDecode() {
        return Stream.of("", "      ", null, "A");
    }
}