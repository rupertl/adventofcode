"""2016 Day 3: Squares With Three Sides"""


# pylint: disable=invalid-name,too-few-public-methods


from puzzle import Puzzle


class Shape:
    """Represents an object with three sides that may be a triangle."""
    def __init__(self, sides):
        self.sides = sides

    def is_triange(self):
        """Is this shape a triange?"""
        return (self.sides[0] + self.sides[1] > self.sides[2] and
                self.sides[1] + self.sides[2] > self.sides[0] and
                self.sides[2] + self.sides[0] > self.sides[1])


class Day03(Puzzle):
    """2016 Day 3: Squares With Three Sides"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 3

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.shapes_horiz = self.parse_input_horiz(lines)
        self.shapes_vert = self.parse_input_vert(lines)

    def parse_input_horiz(self, lines):
        """Convert lines of triangle sides to an array of ints"""
        return [Shape(self.get_sides(line)) for line in lines]

    def parse_input_vert(self, lines):
        """Convert lines of triangle sides - stored vertically - to an
        array of ints"""
        assert len(lines) % 3 == 0
        shapes = []
        for offset in range(0, len(lines), 3):
            row1 = self.get_sides(lines[offset])
            row2 = self.get_sides(lines[offset + 1])
            row3 = self.get_sides(lines[offset + 2])
            for index in range(3):
                shapes.append(Shape([row1[index], row2[index], row3[index]]))
        return shapes

    def get_sides(self, line):
        """Convert eg 3 4 5 to an array of ints."""
        return [int(x) for x in line.strip().split()]

    def calculate_a(self):
        """Part A: How many are valid triangles?"""
        return self.solve(self.shapes_horiz)

    def calculate_b(self):
        """Part B. How many are valid triangles, parsing vertically?"""
        return self.solve(self.shapes_vert)

    def solve(self, shapes):
        """Solve the puzzle by counting how many shapes are triangles."""
        return sum((shape.is_triange() for shape in shapes))
