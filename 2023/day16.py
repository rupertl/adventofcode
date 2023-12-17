"""2023 Day 16: The Floor Will Be Lava"""


# pylint: disable=invalid-name,too-few-public-methods

from collections import namedtuple
from puzzle import Puzzle

Point = namedtuple('Point', 'row col')

# Compass points offsets
N = (-1, 0)
S = (1, 0)
E = (0, 1)
W = (0, -1)

# Marker for when we try to access something outside the grid.
OUT_OF_RANGE = 0

# For a grid piece and a direction, where should our next move be?
TRANSITIONS = {
    ('.', N): N,
    ('.', S): S,
    ('.', E): E,
    ('.', W): W,
    ('/', N): E,
    ('/', S): W,
    ('/', E): N,
    ('/', W): S,
    ('\\', N): W,
    ('\\', S): E,
    ('\\', E): S,
    ('\\', W): N,
    ('|', N): N,
    ('|', S): S,
    ('|', E): (N, S),
    ('|', W): (N, S),
    ('-', N): (E, W),
    ('-', S): (E, W),
    ('-', E): E,
    ('-', W): W,
}


class Ray():
    """A ray of light which has a location, a set of previous moves
    and a direction."""
    def __init__(self, start, direction):
        self.position = start
        self.direction = direction

    def move(self, newDirection):
        """Move ray to a new position based on current position and
        new direction."""
        newPosition = Point(self.position.row + newDirection[0],
                            self.position.col + newDirection[1])
        self.position = newPosition
        self.direction = newDirection


class LavaContraption():
    """Represents a grid of space/mirrors/splitters through which rays
    can pass."""
    def __init__(self, lines):
        self.grid = lines
        self.maxRow = len(self.grid)
        self.maxCol = len(self.grid[0])
        self.energized = set()  # Set of points rays have visited
        self.rayMoves = set()   # Set of ray (position, direction)s

    def reset(self):
        """Reset any previous ray activity in the grid."""
        self.energized.clear()
        self.rayMoves.clear()

    def at(self, point):
        """Return the value of the grid at point, or 0 if not valid."""
        if (point.row < 0 or point.row >= self.maxRow or
           point.col < 0 or point.col >= self.maxCol):
            return OUT_OF_RANGE
        return self.grid[point.row][point.col]

    def project(self, ray):
        """Model a ray of light moving in the grid."""
        while self.at(ray.position) != OUT_OF_RANGE:
            self.energized.add(ray.position)
            move = (ray.position, ray.direction)
            if move in self.rayMoves:
                # A ray travelling in the same direction through this
                # position has already been projected, so we don't
                # need to trace its path further.
                return
            self.rayMoves.add(move)
            transit = TRANSITIONS[(self.at(ray.position), ray.direction)]
            if isinstance(transit[0], tuple):
                # Split into two rays
                newRay = Ray(ray.position, transit[0])
                self.project(newRay)
                transit = transit[1]
            ray.move(transit)

    def energized_viz(self):
        """Create a visualisation of the energized cells."""
        viz = ""
        for rowIndex, row in enumerate(self.grid):
            line = []
            for colIndex, _ in enumerate(row):
                if Point(rowIndex, colIndex) in self.energized:
                    line.append('#')
                else:
                    line.append('.')
            viz += ''.join(line) + "\n"
        return viz

    def entries(self):
        """Return a list of all ray entry points to the grid."""
        rays = []
        for col in range(self.maxCol):
            rays.append(Ray(Point(0, col), S))
            rays.append(Ray(Point(self.maxRow - 1, col), N))
        for row in range(self.maxRow):
            rays.append(Ray(Point(row, 0), E))
            rays.append(Ray(Point(row, self.maxCol - 1), W))
        return rays

    def most_energized(self, rays):
        """Return the max number of energized cells for all rays."""
        results = []
        for ray in rays:
            self.reset()
            self.project(ray)
            results.append(len(self.energized))
        return max(results)


class Day16(Puzzle):
    """2023 Day 16: The Floor Will Be Lava"""
    @classmethod
    def day(cls):
        """Registered day number for this puzzle."""
        return 16

    def __init__(self, puzzle_data):
        super().__init__(puzzle_data)
        lines = self.puzzle_data.input_as_lines()
        self.lc = LavaContraption(lines)
        self.topLeftE = Ray(Point(0, 0), E)

    def calculate_a(self):
        """Part A: With the beam starting in the top-left heading
        right, how many tiles end up being energized?"""
        return self.lc.most_energized((self.topLeftE,))

    def calculate_b(self):
        """Part B. Find the initial beam configuration that energizes
        the largest number of tiles; how many tiles are energized in
        that configuration?"""
        return self.lc.most_energized(self.lc.entries())
