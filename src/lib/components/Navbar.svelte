<script>
  let {
    title = '',
    icon = '',
    links = [],
    githubUrl = ''
  } = $props();

  let mobileMenuOpen = $state(false);

  function scrollToTop(e) {
    e.preventDefault();
    window.scrollTo({ top: 0, behavior: 'smooth' });
    mobileMenuOpen = false;
  }
</script>

<nav class="sticky top-0 z-50 bg-[#0d1117]/95 border-b border-[#30363d] backdrop-blur-md">
  <div class="max-w-[1080px] mx-auto px-5 h-[60px] flex items-center justify-between">
    <!-- Brand -->
    <!-- svelte-ignore a11y_invalid_attribute -->
    <a href="#top" class="flex items-center gap-2.5 text-[#e6edf3] font-semibold text-base hover:text-white transition-colors cursor-pointer" onclick={scrollToTop} title={title}>
      {#if icon}
        <img src={icon} alt="{title} Icon" class="w-7 h-7 rounded object-cover" />
      {/if}
      <span>{title}</span>
    </a>

    <!-- Desktop Nav Links -->
    <ul class="hidden md:flex items-center gap-6 list-none m-0 p-0">
      {#each links as { href, label }}
        <li>
          <a {href} class="text-sm font-medium text-[#8b949e] hover:text-[#e6edf3] transition-colors">
            {label}
          </a>
        </li>
      {/each}
      {#if githubUrl}
        <li>
          <a
            href={githubUrl}
            target="_blank"
            rel="noopener"
            class="flex items-center text-[#8b949e] hover:text-[#e6edf3] p-1.5 rounded-md hover:bg-[#21262d] transition-colors"
            title="View on GitHub"
          >
            <svg viewBox="0 0 16 16" class="w-5 h-5 fill-current" fill="currentColor">
              <path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"/>
            </svg>
          </a>
        </li>
      {/if}
    </ul>

    <!-- Mobile Menu Button -->
    <button
      class="md:hidden p-1 text-[#8b949e] hover:text-[#e6edf3] cursor-pointer"
      onclick={() => (mobileMenuOpen = !mobileMenuOpen)}
      aria-label="Toggle navigation"
    >
      <svg viewBox="0 0 24 24" class="w-[22px] h-[22px] stroke-current stroke-2 fill-none" stroke="currentColor">
        <line x1="3" y1="12" x2="21" y2="12"></line>
        <line x1="3" y1="6" x2="21" y2="6"></line>
        <line x1="3" y1="18" x2="21" y2="18"></line>
      </svg>
    </button>
  </div>

  <!-- Mobile Dropdown Menu -->
  {#if mobileMenuOpen}
    <div class="md:hidden bg-[#161b22] border-t border-[#30363d] px-5 py-3">
      <ul class="flex flex-col gap-2.5 list-none m-0 p-0">
        {#each links as { href, label }}
          <li>
            <a
              {href}
              class="block py-1.5 text-sm text-[#8b949e] hover:text-[#e6edf3] transition-colors"
              onclick={() => (mobileMenuOpen = false)}
            >
              {label}
            </a>
          </li>
        {/each}
        {#if githubUrl}
          <li>
            <a
              href={githubUrl}
              target="_blank"
              rel="noopener"
              class="flex items-center gap-2 py-1.5 text-sm text-[#8b949e] hover:text-[#e6edf3] transition-colors"
            >
              <svg viewBox="0 0 16 16" class="w-4 h-4 fill-current" fill="currentColor">
                <path d="M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82.64-.18 1.32-.27 2-.27.68 0 1.36.09 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0016 8c0-4.42-3.58-8-8-8z"/>
              </svg>
              <span>GitHub</span>
            </a>
          </li>
        {/if}
      </ul>
    </div>
  {/if}
</nav>
