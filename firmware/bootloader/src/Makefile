##############################################################################
# Build global options
# NOTE: Can be overridden externally.
#

# by default EXTRA_PARAMS is empty and we create 'debug' version of the firmware with additional assertions and statistics
# for 'release' options see 'clean_compile_two_versions.bat' file

ifeq ($(DEBUG_LEVEL_OPT),)
  # this value would be used by default. For 'debug' configuration override with '-O0 -ggdb -g3' or something along these lines
  DEBUG_LEVEL_OPT = -O2
  DDEFS += -DEFI_ENABLE_ASSERTS=FALSE -DCH_DBG_ENABLE_TRACE=FALSE -DCH_DBG_ENABLE_ASSERTS=FALSE -DCH_DBG_ENABLE_STACK_CHECK=FALSE -DCH_DBG_FILL_THREADS=FALSE -DCH_DBG_THREADS_PROFILING=FALSE
endif

# disable some modules to shrink bootloader binary
DDEFS += -DHAL_USE_ADC=FALSE -DHAL_USE_CAN=FALSE -DHAL_USE_EXT=FALSE -DHAL_USE_GPT=FALSE -DHAL_USE_I2C=FALSE -DHAL_USE_ICU=FALSE -DHAL_USE_PWM=FALSE -DHAL_USE_RTC=FALSE -DHAL_USE_I2C=FALSE 

# disable USB (The bootloader has currently UART support only)
DDEFS += -DEFI_USB_SERIAL=FALSE -DHAL_USE_SERIAL_USB=FALSE -DHAL_USE_USB=FALSE -DHAL_USE_USB_MSD=FALSE


# Compiler options here.
ifeq ($(USE_OPT),)
  USE_OPT = $(EXTRA_PARAMS) $(DEBUG_LEVEL_OPT) $(RFLAGS) -fomit-frame-pointer -falign-functions=16 -Werror-implicit-function-declaration -Werror -Wno-error=pointer-sign -Wno-error=unused-function -Wno-error=unused-variable -Wno-error=sign-compare -Wno-error=unused-parameter -Wno-error=missing-field-initializers -Werror=type-limits -Wno-error=strict-aliasing -Wno-error=attributes
endif

#USE_OPT += -fPIC

# C specific options here (added to USE_OPT).
ifeq ($(USE_COPT),)
  USE_COPT = -fgnu89-inline -std=gnu99
endif

# C++ specific options here (added to USE_OPT).
ifeq ($(USE_CPPOPT),)
  USE_CPPOPT = -std=c++11 -fno-rtti -fno-exceptions -fno-use-cxa-atexit -Werror=write-strings -Werror=type-limits
endif

# Enable this if you want the linker to remove unused code and data
ifeq ($(USE_LINK_GC),)
  USE_LINK_GC = yes
endif

# Linker extra options here.
ifeq ($(USE_LDOPT),)
  USE_LDOPT =
endif

# Enable this if you want link time optimizations (LTO)
ifeq ($(USE_LTO),)
  USE_LTO = no
endif

# If enabled, this option allows to compile the application in THUMB mode.
ifeq ($(USE_THUMB),)
  USE_THUMB = yes
endif

# Enable this if you want to see the full log while compiling.
ifeq ($(USE_VERBOSE_COMPILE),)
  USE_VERBOSE_COMPILE = no
endif

# If enabled, this option makes the build process faster by not compiling
# modules not used in the current configuration.
ifeq ($(USE_SMART_BUILD),)
  USE_SMART_BUILD = no
endif

#
# Build global options
##############################################################################

##############################################################################
# Architecture or project specific options
#

# Stack size to be allocated to the Cortex-M process stack. This stack is
# the stack used by the main() thread.
ifeq ($(USE_PROCESS_STACKSIZE),)
  USE_PROCESS_STACKSIZE = 0x0600
endif

# Stack size to the allocated to the Cortex-M main/exceptions stack. This
# stack is used for processing interrupts and exceptions.
ifeq ($(USE_EXCEPTIONS_STACKSIZE),)
  USE_EXCEPTIONS_STACKSIZE = 0x1000
endif

# Enables the use of FPU on Cortex-M4 (no, softfp, hard).
ifeq ($(USE_FPU),)
  USE_FPU = softfp
endif

#
# Architecture or project specific options
##############################################################################

##############################################################################
# Project, sources and paths
#

# Define project name here
PROJECT = bootloader
PROJECT_DIR = ..

# Imported source files and paths
CHIBIOS = $(PROJECT_DIR)/ChibiOS3
CHIBIOS_CONTRIB = $(PROJECT_DIR)/ChibiOS-Contrib

ifeq ($(PROJECT_BOARD),)
  PROJECT_BOARD = ST_STM32F4
endif
DDEFS += -D$(PROJECT_BOARD)

CONFIG = $(PROJECT_DIR)/config

# Startup files.
include $(CHIBIOS)/os/common/ports/ARMCMx/compilers/GCC/mk/startup_stm32f4xx.mk
# HAL-OSAL files (optional).
include $(CHIBIOS_CONTRIB)/os/hal/hal.mk
include $(CHIBIOS_CONTRIB)/os/hal/ports/STM32/STM32F4xx/platform.mk
include $(CHIBIOS)/os/hal/osal/rt/osal.mk
# RTOS files (optional).
include $(CHIBIOS)/os/rt/rt.mk
include $(CHIBIOS)/os/rt/ports/ARMCMx/compilers/GCC/mk/port_v7m.mk
include $(CHIBIOS)/os/various/cpp_wrappers/chcpp.mk

include $(CONFIG)/boards/$(PROJECT_BOARD)/board.mk

# Define linker script file here
ifeq ($(LDSCRIPT),)
  LDSCRIPT= $(CONFIG)/stm32f4ems/STM32F407xG_CCM.ld
endif

# C sources that can be compiled in ARM or THUMB mode depending on the global
# setting.
CSRC = $(STARTUPSRC) \
	$(KERNSRC) \
	$(PORTSRC) \
	$(OSALSRC) \
	$(HALSRC) \
	$(PLATFORMSRC) \
	$(BOARDSRC) \
	$(CHIBIOS)/os/various/syscalls.c \
	$(CHIBIOS)/os/hal/lib/streams/memstreams.c \
	$(CHIBIOS)/os/hal/lib/streams/chprintf.c \
	$(CHIBIOS)/os/various/shell.c \
	$(UTILSRC) \
	$(ENGINES_SRC) \
	$(CONSOLESRC) \
	$(DEV_SRC) \
	$(HW_LAYER_EMS) \
	$(CONTROLLERSSRC) \
	$(CONTROLLERS_ALGO_SRC) \
	$(CONTROLLERS_CORE_SRC) \
	$(CONTROLLERS_SENSORS_SRC) \
	$(FATFSSRC) \
	$(SYSTEMSRC) \
	$(PROJECT_DIR)/hw_layer/flash.c

# C++ sources that can be compiled in ARM or THUMB mode depending on the global
# setting.
CPPSRC = $(CHCPPSRC) \
	$(TRIGGER_SRC_CPP) \
	$(TRIGGER_DECODERS_SRC_CPP) \
	$(DEV_SRC_CPP) \
	$(CONTROLLERS_ALGO_SRC_CPP) \
	$(SYSTEMSRC_CPP) \
	$(BOARDSRC_CPP) \
	$(ENGINES_SRC_CPP) \
	$(HW_LAYER_EMS_CPP) \
	$(HW_SENSORS_SRC) \
	$(TUNERSTUDIO_SRC_CPP) \
	$(CONSOLE_SRC_CPP) \
	$(CONTROLLERS_SENSORS_SRC_CPP) \
	$(CONTROLLERS_SRC_CPP) \
	$(UTILSRC_CPP) \
	$(CONTROLLERS_CORE_SRC_CPP) \
	$(CONTROLLERS_MATH_SRC_CPP) \
	$(PROJECT_DIR)/console/binary/tunerstudio_io.cpp \
	$(PROJECT_DIR)/controllers/system/efiGpio.cpp \
	$(PROJECT_DIR)/controllers/algo/engine_configuration.cpp \
	$(PROJECT_DIR)/controllers/persistent_store.cpp \
	$(PROJECT_DIR)/hw_layer/io_pins.cpp \
	$(PROJECT_DIR)/hw_layer/pin_repository.cpp \
	src/rusefi_stubs.cpp \
	src/dfu.cpp \
	src/main.cpp

# C sources to be compiled in ARM mode regardless of the global setting.
# NOTE: Mixing ARM and THUMB mode enables the -mthumb-interwork compiler
#       option that results in lower performance and larger code size.
ACSRC =

# C++ sources to be compiled in ARM mode regardless of the global setting.
# NOTE: Mixing ARM and THUMB mode enables the -mthumb-interwork compiler
#       option that results in lower performance and larger code size.
ACPPSRC =

# C sources to be compiled in THUMB mode regardless of the global setting.
# NOTE: Mixing ARM and THUMB mode enables the -mthumb-interwork compiler
#       option that results in lower performance and larger code size.
TCSRC =

# C sources to be compiled in THUMB mode regardless of the global setting.
# NOTE: Mixing ARM and THUMB mode enables the -mthumb-interwork compiler
#       option that results in lower performance and larger code size.
TCPPSRC =

# List ASM source files here
# List ASM source files here
ASMSRC = $(STARTUPASM) $(PORTASM) $(OSALASM)

INCDIR = .. \
	$(PORTINC) \
	$(OSALINC) \
	$(KERNINC) \
	$(TESTINC) \
	$(STARTUPINC) \
	$(HALINC) \
	$(PLATFORMINC) \
	$(BOARDINC) \
	$(CHCPPINC) \
	$(CHIBIOS)/os/hal/lib/streams \
	$(CHIBIOS)/os/various \
	$(CHIBIOS)/os/various/devices_lib/accel \
    $(CHIBIOS_CONTRIB)/os/various \
	$(CONFIG)/stm32f4ems \
	$(CHIBIOS)/os/various \
	$(CONFIG)/stm32f4ems \
	$(CONFIG)/engines \
	$(PROJECT_DIR)/ext \
	$(PROJECT_DIR)/ext_algo \
	$(PROJECT_DIR)/util \
	$(PROJECT_DIR)/console_util \
	$(PROJECT_DIR)/console \
	$(PROJECT_DIR)/console/binary \
	$(PROJECT_DIR)/console/fl_binary \
	$(PROJECT_DIR)/hw_layer \
	$(PROJECT_DIR)/mass_storage \
	$(PROJECT_DIR)/hw_layer/serial_over_usb \
	$(PROJECT_DIR)/hw_layer/algo \
	$(PROJECT_DIR)/hw_layer/lcd \
	$(PROJECT_DIR)/hw_layer/sensors \
	$(PROJECT_DIR)/hw_layer/mass_storage \
	$(PROJECT_DIR)/hw_layer/stm32f4 \
	$(PROJECT_DIR)/development \
	$(PROJECT_DIR)/development/hw_layer \
	$(PROJECT_DIR)/development/test \
	$(PROJECT_DIR)/controllers \
	$(PROJECT_DIR)/controllers/sensors \
	$(PROJECT_DIR)/controllers/system \
	$(PROJECT_DIR)/controllers/algo \
	$(PROJECT_DIR)/controllers/core \
	$(PROJECT_DIR)/controllers/math \
	$(PROJECT_DIR)/controllers/trigger/decoders \
	$(PROJECT_DIR)/controllers/trigger \
	config

BUILDDIR=blbuild

#
# Project, sources and paths
##############################################################################

##############################################################################
# Compiler settings
#

MCU  = cortex-m4

#TRGT = arm-elf-
TRGT = arm-none-eabi-
CC   = $(TRGT)gcc
CPPC = $(TRGT)g++
# Enable loading with g++ only if you need C++ runtime support.
# NOTE: You can use C++ even without C++ support if you are careful. C++
#       runtime support makes code size explode.
LD   = $(TRGT)gcc
#LD   = $(TRGT)g++
CP   = $(TRGT)objcopy
AS   = $(TRGT)gcc -x assembler-with-cpp
AR   = $(TRGT)ar
OD   = $(TRGT)objdump
SZ   = $(TRGT)size
HEX  = $(CP) -O ihex
BIN  = $(CP) -O binary

# ARM-specific options here
AOPT =

# THUMB-specific options here
TOPT = -mthumb -DTHUMB

# Define C warning options here
CWARN = -Wall -Wextra -Wstrict-prototypes

# Define C++ warning options here
CPPWARN = -Wall -Wextra

#
# Compiler settings
##############################################################################

##############################################################################
# Start of user section
#

# List all user C define here, like -D_DEBUG=1
UDEFS =

# Define ASM defines here
UADEFS =

# List all user directories here
UINCDIR =

# List the user directory to look for the libraries here
ULIBDIR =

# List all user libraries here
ULIBS = -lm

#
# End of user defines
##############################################################################

RULESPATH = $(CHIBIOS)/os/common/ports/ARMCMx/compilers/GCC
include $(RULESPATH)/rules.mk
