install(
    TARGETS aoc2017_exe
    RUNTIME COMPONENT aoc2017_Runtime
)

if(PROJECT_IS_TOP_LEVEL)
  include(CPack)
endif()
