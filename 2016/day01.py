"""2016 Day 1: No Time for a Taxicab"""


# pylint: disable=invalid-name,too-few-public-methods


from puzzle import Puzzle


class GridMove:
    """Convert an instruction like R3 to a rotation and number of steps."""
    def __init__(self, instruction):
        turn = instruction[0]
        assert turn in ['L', 'R']
        self.rotation = 90 if turn == 'R' else -90
        self.distance = int(instruction[1:])


def manhattan_distance(x, y):
    """Return the Manhattan (grid) dfistance from the origin to point (x,y)."""
    return abs(x) + abs(y)


class GridPosition:
    """Represents facing and x/y coordinates on a city grid."""
    def __init__(self):
        self.facing = 0         # Angle in degrees, 0 is North
        self.pos_x = 0
        self.pos_y = 0
        self.visited = set()    # Set of points visited
        self.revisited = None   # First point visited more than once
        self.add_visited()      # We have visited the origin

    def add_visited(self):
        """Store our current location in set of points visited, and
        sets revisited if this is the first time we have revisited any
        point."""
        pos = (self.pos_x, self.pos_y)
        if pos in self.visited and not self.revisited:
            self.revisited = pos
        self.visited.add(pos)

    def move(self, grid_move):
        """Use grid_move to move to a new position on the grid."""
        self.set_facing(grid_move.rotation)
        for _ in range(grid_move.distance):
            if self.facing == 0:
                self.pos_y += 1
            elif self.facing == 90:
                self.pos_x += 1
            elif self.facing == 180:
                self.pos_y -= 1
            elif self.facing == 270:
                self.pos_x -= 1
            self.add_visited()

    def set_facing(self, rotation):
        """Adjust facing by given rotation."""
        self.facing += rotation
        if self.facing < 0:
            self.facing += 360
        elif self.facing >= 360:
            self.facing %= 360

    def distance(self):
        """Returns Manhattan distance from origin."""
        return manhattan_distance(self.pos_x, self.pos_y)

    def revisited_distance(self):
        """Returns Manhattan distance from origin of the first
        revisited point."""
        if self.revisited:
            return manhattan_distance(self.revisited[0], self.revisited[1])
        raise RuntimeError("not revisited any points")


class Day01(Puzzle):
    """2016 Day 1: No Time for a Taxicab"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 1

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        self.moves = self.parse_input(self.puzzle_data.input_as_string())

    def parse_input(self, line):
        """Convert comma separated list of instructions like R3, L2 to
        list of Directions."""
        return [GridMove(item) for item in line.rstrip().split(', ')]

    def calculate_a(self):
        """Part A: How many blocks away?"""
        return self.solve().distance()

    def calculate_b(self):
        """Part B. Which is the first point we visited twice?"""
        return self.solve().revisited_distance()

    def solve(self):
        """Run moves through GridPosition and return the object."""
        grid_pos = GridPosition()
        for move in self.moves:
            grid_pos.move(move)
        return grid_pos
