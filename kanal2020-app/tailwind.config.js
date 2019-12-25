module.exports = {
  corePlugins: {
    outline: false,
    preflight: true,
  },
  theme: {
    extend: {}
  },
  variants: {
    outline: []
  },
  plugins: [
    function ({ addBase, config }) {
      addBase({
        'button:focus': { outline: 'none' }
      })
    },
  ]
}
